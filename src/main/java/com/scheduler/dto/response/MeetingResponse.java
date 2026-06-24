package com.scheduler.dto.response;

import java.util.List;

public record MeetingResponse(
        String title,
        String description,
        String slotNumber,
        List<String> participants
) {
}
