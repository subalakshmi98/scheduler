package com.scheduler.dto.request;

public record CreateMeetingRequest(
        String title,
        String description
) {
}
