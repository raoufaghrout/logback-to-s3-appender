package com.unrulymedia.logback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingApplication {

    public static void main(String[] args) {

        Logger logger = LoggerFactory.getLogger(LoggingApplication.class);

        logger.trace("This is a trace log!");
        logger.debug("This is a debug log!");
        logger.info("This is an info log!");
        logger.warn("This is a warning log!");
        logger.error("This is an error log!");
    }
}
