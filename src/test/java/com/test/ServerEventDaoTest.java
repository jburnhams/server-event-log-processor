package com.test;

import com.test.domain.ServerEvent;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class ServerEventDaoTest {

    @Test
    public void shouldStoreAndRetrieveServerEvent() {
        ServerEventDao serverEventDao = new ServerEventDao("jdbc:hsqldb:mem:testdb");

        ServerEvent event = new ServerEvent();
        event.setId("123");
        event.setDuration(156L);
        event.setType("TYPE1");
        event.setHost("HOST1");
        event.setAlert(true);

        serverEventDao.store(event);

        Optional<ServerEvent> resultOptional = serverEventDao.load("123");
        assertThat(resultOptional).isPresent();
        ServerEvent result = resultOptional.get();
        assertThat(result.getId()).isEqualTo("123");
        assertThat(result.getDuration()).isEqualTo(156L);
        assertThat(result.getType()).isEqualTo("TYPE1");
        assertThat(result.getHost()).isEqualTo("HOST1");
        assertThat(result.isAlert()).isTrue();
    }

}