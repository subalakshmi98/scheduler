package com.scheduler.exception;

public class InvalidTimeSlotException extends RuntimeException {

    public InvalidTimeSlotException() {
        super("End time must be after start time");
    }
}