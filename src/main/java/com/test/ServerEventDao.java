package com.test;

import com.test.domain.ServerEvent;
import org.jdbi.v3.core.Jdbi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class ServerEventDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerEventDao.class);

    private final Jdbi jdbi;

    public ServerEventDao() {
        this("jdbc:hsqldb:file:testdb");
    }

    public ServerEventDao(String jdbcUrl) {
        //TODO connection pooling
        jdbi = Jdbi.create(jdbcUrl);
        jdbi.withHandle(h -> h.execute("create table if not exists events (" +
                "id varchar(128) not null primary key," +
                "duration bigint not null," +
                "alert boolean not null," +
                "host varchar(512)," +
                "type varchar(512))"));
    }

    public void store(ServerEvent event) {
        LOGGER.debug("Storing "+event);
        jdbi.useHandle(h->h.execute("insert into events (id, duration, alert, host, type) values (?,?,?,?,?)",
                event.getId(),
                event.getDuration(),
                event.isAlert(),
                event.getHost(),
                event.getType()
        ));
    }

    public Optional<ServerEvent> load(String id) {
        return jdbi.withHandle(h->
                h.createQuery("select * from events where id = ?")
                        .bind(0, id)
                        .mapToBean(ServerEvent.class)
                        .findFirst());
    }
}
