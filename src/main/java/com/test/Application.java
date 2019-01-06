package com.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Paths;

public class Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    public static void main(String [] args) throws IOException {
        if (args.length == 0) {
            throw new IllegalArgumentException("Supply input file path as first agruement");
        }

        Service service = new Service(new ServerEventDao());
        service.processLogFile(Paths.get(args[0]));

    }

}
