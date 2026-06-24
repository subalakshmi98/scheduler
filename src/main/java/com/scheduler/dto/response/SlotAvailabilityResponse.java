package com.scheduler.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.scheduler.constants.SlotStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record SlotAvailabilityResponse(
        String slotNumber,
        UUID slotId,
        LocalDateTime startTime,
        LocalDateTime endTime,
        SlotStatus status,
        String meetingTitle
) {
}
