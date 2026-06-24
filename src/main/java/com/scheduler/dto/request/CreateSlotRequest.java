package com.scheduler.dto.request;

import java.time.LocalDateTime;

public record CreateSlotRequest(
        LocalDateTime startTime,
        LocalDateTime endTime
) {
}
