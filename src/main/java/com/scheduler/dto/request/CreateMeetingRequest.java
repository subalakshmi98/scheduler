package com.scheduler.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record CreateMeetingRequest(
        @NotBlank
        String title,
        String description,
        List<ParticipantRequest> participants
) {
}
