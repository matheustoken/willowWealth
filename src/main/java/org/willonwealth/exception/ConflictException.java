package org.willonwealth.exception;

import java.util.List;

public class ConflictException extends RuntimeException {
    private final List<String> details;
    public ConflictException(String message, List<String> details) {
        super(message);
        this.details = details;
    }
    public List<String> getDetails() { return details; }
}