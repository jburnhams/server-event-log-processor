package com.test;

import com.test.domain.LogEntry;
import com.test.domain.ServerEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

public class LogAggregator implements Function<LogEntry, Optional<ServerEvent>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogAggregator.class);

    //TODO should be configuration
    private final static long ALERT_THRESHOLD = 4L;

    private final ConcurrentMap<String, LogEntry> openEvents = new ConcurrentHashMap<>();

    @Override
    public Optional<ServerEvent> apply(LogEntry logEntry) {
        LogEntry existing = openEvents.putIfAbsent(logEntry.getId(), logEntry);
        if (existing == null) {
            return Optional.empty();
        } else {
            openEvents.remove(logEntry.getId());
            ServerEvent serverEvent = new ServerEvent();
            serverEvent.setId(logEntry.getId());
            serverEvent.setHost(logEntry.getHost() == null ? existing.getHost() : logEntry.getHost());
            serverEvent.setType(logEntry.getType() == null ? existing.getType() : logEntry.getType());
            serverEvent.setDuration(Math.abs(logEntry.getTimestamp() - existing.getTimestamp()));
            serverEvent.setAlert(serverEvent.getDuration() > ALERT_THRESHOLD);
            LOGGER.debug("Found complete "+serverEvent);
            return Optional.of(serverEvent);
        }
    }

    public int openEventsCount() {
        return openEvents.size();
    }
}
