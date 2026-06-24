package com.scheduler.dto.response;

public record MeetingResponse(
        String title,
        String description,
        String slotNumber
) {
}
