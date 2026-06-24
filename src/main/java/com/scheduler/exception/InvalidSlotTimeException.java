package com.scheduler.exception;

public class InvalidSlotTimeException extends RuntimeException {

    public InvalidSlotTimeException() {
        super("End time must be after start time");
    }
}