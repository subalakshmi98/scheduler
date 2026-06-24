package com.scheduler.dto.response;

import com.scheduler.constants.SlotStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record SlotAvailabilityResponse(
        String slotNumber,
        UUID slotId,
        LocalDateTime startTime,
        LocalDateTime endTime,
        SlotStatus status
) {
}
