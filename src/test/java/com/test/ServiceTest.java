package com.test;

import com.test.domain.ServerEvent;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class ServiceTest {

    @Test
    public void shouldProcessSampleFile() throws URISyntaxException, IOException {
        Path source = Paths.get(getClass().getResource("/sample.log").toURI());

        ServerEventDao dao = new ServerEventDao("jdbc:hsqldb:mem:testdb");
        Service service = new Service(dao);

        long events = service.processLogFile(source);

        assertThat(events).isEqualTo(3);

        Optional<ServerEvent> optional1 = dao.load("scsmbstgra");
        assertThat(optional1).isPresent();
        ServerEvent event1 = optional1.get();
        assertThat(event1.getId()).isEqualTo("scsmbstgra");
        assertThat(event1.getHost()).isEqualTo("12345");
        assertThat(event1.getType()).isEqualTo("APPLICATION_LOG");
        assertThat(event1.getDuration()).isEqualTo(5L);
        assertThat(event1.isAlert()).isTrue();

        Optional<ServerEvent> optional2 = dao.load("scsmbstgrb");
        assertThat(optional2).isPresent();
        ServerEvent event2 = optional2.get();
        assertThat(event2.getId()).isEqualTo("scsmbstgrb");
        assertThat(event2.getHost()).isNull();
        assertThat(event2.getType()).isNull();
        assertThat(event2.getDuration()).isEqualTo(3L);
        assertThat(event2.isAlert()).isFalse();

        Optional<ServerEvent> optional3 = dao.load("scsmbstgrc");
        assertThat(optional3).isPresent();
        ServerEvent event3 = optional3.get();
        assertThat(event3.getId()).isEqualTo("scsmbstgrc");
        assertThat(event3.getHost()).isNull();
        assertThat(event3.getType()).isNull();
        assertThat(event3.getDuration()).isEqualTo(8L);
        assertThat(event3.isAlert()).isTrue();
    }




}