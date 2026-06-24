package com.scheduler.dto.response;

import com.scheduler.constants.SlotStatus;

import java.time.LocalDateTime;

public record SlotResponse(
        String slotNumber,
        LocalDateTime startTime,
        LocalDateTime endTime,
        SlotStatus status
) {
}
