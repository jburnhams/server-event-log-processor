package com.test;

import com.test.domain.LogEntry;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class LogParserTest {

    @Test
    public void shouldParseEachLineToJson() throws URISyntaxException, IOException {
        LogParser target = new LogParser();
        Path source = Paths.get(getClass().getResource("/sample.log").toURI());

        try (Stream<String> lines = Files.lines(source)) {
            List<LogEntry> resultList = lines.map(target).collect(Collectors.toList());
            assertThat(resultList).hasSize(6);

            assertThat(resultList.get(0).getId()).isEqualTo("scsmbstgra");
            assertThat(resultList.get(0).getState()).isEqualTo(LogEntry.State.STARTED);
            assertThat(resultList.get(0).getType()).isEqualTo("APPLICATION_LOG");
            assertThat(resultList.get(0).getHost()).isEqualTo("12345");
            assertThat(resultList.get(0).getTimestamp()).isEqualTo(1491377495212L);

            assertThat(resultList.get(1).getId()).isEqualTo("scsmbstgrb");
            assertThat(resultList.get(1).getState()).isEqualTo(LogEntry.State.STARTED);
            assertThat(resultList.get(1).getType()).isNull();
            assertThat(resultList.get(1).getHost()).isNull();
            assertThat(resultList.get(1).getTimestamp()).isEqualTo(1491377495213L);

            assertThat(resultList.get(2).getId()).isEqualTo("scsmbstgrc");
            assertThat(resultList.get(2).getState()).isEqualTo(LogEntry.State.FINISHED);
            assertThat(resultList.get(2).getType()).isNull();
            assertThat(resultList.get(2).getHost()).isNull();
            assertThat(resultList.get(2).getTimestamp()).isEqualTo(1491377495218L);

            assertThat(resultList.get(3).getId()).isEqualTo("scsmbstgra");
            assertThat(resultList.get(3).getState()).isEqualTo(LogEntry.State.FINISHED);
            assertThat(resultList.get(3).getType()).isEqualTo("APPLICATION_LOG");
            assertThat(resultList.get(3).getHost()).isEqualTo("12345");
            assertThat(resultList.get(3).getTimestamp()).isEqualTo(1491377495217L);

            assertThat(resultList.get(4).getId()).isEqualTo("scsmbstgrc");
            assertThat(resultList.get(4).getState()).isEqualTo(LogEntry.State.STARTED);
            assertThat(resultList.get(4).getType()).isNull();
            assertThat(resultList.get(4).getHost()).isNull();
            assertThat(resultList.get(4).getTimestamp()).isEqualTo(1491377495210L);

            assertThat(resultList.get(5).getId()).isEqualTo("scsmbstgrb");
            assertThat(resultList.get(5).getState()).isEqualTo(LogEntry.State.FINISHED);
            assertThat(resultList.get(5).getType()).isNull();
            assertThat(resultList.get(5).getHost()).isNull();
            assertThat(resultList.get(5).getTimestamp()).isEqualTo(1491377495216L);
        }
    }

    //TODO negative cases - parse errors etc. for now doing TDD but on the happy path

}