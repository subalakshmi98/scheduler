package com.scheduler.dto.request;

import java.time.LocalDateTime;

public record UpdateSlotRequest(
        LocalDateTime startTime,
        LocalDateTime endTime
) {
}
