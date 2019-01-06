package com.test;

import com.test.domain.LogEntry;
import com.test.domain.ServerEvent;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class LogAggregatorTest {

    @Test
    public void shouldReturnEmptyIfSingleStarted() {
        LogEntry entry = new LogEntry().setId("123").setState(LogEntry.State.STARTED).setTimestamp(1491377495213L);

        LogAggregator target = new LogAggregator();

        Optional<ServerEvent> result = target.apply(entry);

        assertThat(result).isEmpty();
        assertThat(target.openEventsCount()).isEqualTo(1);
    }

    @Test
    public void shouldReturnEmptyIfSingleFinished() {
        LogEntry entry = new LogEntry().setId("123").setState(LogEntry.State.FINISHED).setTimestamp(1491377495213L);

        LogAggregator target = new LogAggregator();

        Optional<ServerEvent> result = target.apply(entry);

        assertThat(result).isEmpty();
        assertThat(target.openEventsCount()).isEqualTo(1);
    }

    @Test
    public void shouldReturnEmptyIfDifferentIdStartFinished() {
        LogEntry entry1 = new LogEntry().setId("123").setState(LogEntry.State.STARTED).setHost("host1").setTimestamp(1491377495213L);
        LogEntry entry2 = new LogEntry().setId("321").setState(LogEntry.State.FINISHED).setType("EVENT_TYPE").setTimestamp(1491377495215L);

        LogAggregator target = new LogAggregator();

        Optional<ServerEvent> result1 = target.apply(entry1);
        Optional<ServerEvent> result2 = target.apply(entry2);

        assertThat(result1).isEmpty();
        assertThat(result2).isEmpty();
        assertThat(target.openEventsCount()).isEqualTo(2);
    }

    @Test
    public void shouldReturnEventIfStartedThenFinished() {
        LogEntry entry1 = new LogEntry().setId("123").setState(LogEntry.State.STARTED).setHost("host1").setTimestamp(1491377495213L);
        LogEntry entry2 = new LogEntry().setId("123").setState(LogEntry.State.FINISHED).setType("EVENT_TYPE").setTimestamp(1491377495215L);

        LogAggregator target = new LogAggregator();

        Optional<ServerEvent> result1 = target.apply(entry1);
        Optional<ServerEvent> result2 = target.apply(entry2);

        assertThat(result1).isEmpty();
        assertThat(result2).isNotEmpty();
        assertThat(result2.get().getId()).isEqualTo("123");
        assertThat(result2.get().getDuration()).isEqualTo(2L);
        assertThat(result2.get().getHost()).isEqualTo("host1");
        assertThat(result2.get().getType()).isEqualTo("EVENT_TYPE");
        assertThat(result2.get().isAlert()).isFalse();
        assertThat(target.openEventsCount()).isEqualTo(0);
    }

    @Test
    public void shouldReturnEventIfFinishedThenStarted() {
        LogEntry entry1 = new LogEntry().setId("123").setState(LogEntry.State.FINISHED).setType("EVENT_TYPE").setTimestamp(1491377495225L);
        LogEntry entry2 = new LogEntry().setId("123").setState(LogEntry.State.STARTED).setHost("host1").setTimestamp(1491377495213L);

        LogAggregator target = new LogAggregator();

        Optional<ServerEvent> result1 = target.apply(entry1);
        Optional<ServerEvent> result2 = target.apply(entry2);

        assertThat(result1).isEmpty();
        assertThat(result2).isNotEmpty();
        assertThat(result2.get().getId()).isEqualTo("123");
        assertThat(result2.get().getDuration()).isEqualTo(12L);
        assertThat(result2.get().getHost()).isEqualTo("host1");
        assertThat(result2.get().getType()).isEqualTo("EVENT_TYPE");
        assertThat(result2.get().isAlert()).isTrue();
        assertThat(target.openEventsCount()).isEqualTo(0);
    }

}