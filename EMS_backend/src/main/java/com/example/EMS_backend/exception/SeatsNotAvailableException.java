package com.example.EMS_backend.exception;

public class SeatsNotAvailableException extends RuntimeException {
    public SeatsNotAvailableException(String message) {
        super(message);
    }
}
