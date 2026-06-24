package com.scheduler.exception;

public class SlotOverlapException extends RuntimeException {

    public SlotOverlapException() {
        super("Slot overlaps existing slot");
    }
}