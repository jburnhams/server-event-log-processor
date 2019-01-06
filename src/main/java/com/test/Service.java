package com.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Stream;

public class Service {

    private static final Logger LOGGER = LoggerFactory.getLogger(Service.class);

    private final ServerEventDao serverEventDao;

    public Service(ServerEventDao serverEventDao) {
        this.serverEventDao = serverEventDao;
    }

    public long processLogFile(Path input) throws IOException {
        LOGGER.info("Starting to process file "+input);
        LogParser parser = new LogParser();
        LogAggregator aggregator = new LogAggregator();

        long events = processFile(input, parser, aggregator);

        LOGGER.info("Completed processing of file "+input+", "+events+" events found");

        return events;
    }

    private long processFile(Path input, LogParser parser, LogAggregator aggregator) throws IOException {
        try (Stream<String> lines = Files.lines(input)) {
            return lines
                    .parallel()
                    .map(parser)
                    .map(aggregator)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .peek(serverEventDao::store)
                    .count();
        }
    }


}
