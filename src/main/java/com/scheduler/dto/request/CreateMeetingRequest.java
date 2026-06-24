package com.scheduler.dto.request;

import java.util.List;

public record CreateMeetingRequest(
        String title,
        String description,
        List<ParticipantRequest> participants
) {
}
