package com.scheduler.exception;

public class SlotNotFoundException extends RuntimeException {

    public SlotNotFoundException(String slotNumber) {
        super("Slot not found: " + slotNumber);
    }
}