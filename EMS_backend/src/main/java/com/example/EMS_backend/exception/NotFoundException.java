package com.example.EMS_backend.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotFoundException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(NotFoundException.class);

    int id;
    String value;

    public NotFoundException(int id,String value) {
        this.id = id;
        this.value = value;

        logger.error("NotFoundException thrown: {} with ID: {}", value, id);
    }

    @Override
    public String getMessage() {
        return value + " with ID: " + id + " does not exist!";
    }
}
