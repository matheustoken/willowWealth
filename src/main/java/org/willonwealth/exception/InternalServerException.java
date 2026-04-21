package org.willonwealth.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public class InternalServerException extends RuntimeException {
    private final List<String> details;
    public InternalServerException(String message, List<String> details) {
        super(message);
        this.details = details;
    }
    public List<String> getDetails() { return details; }
}

