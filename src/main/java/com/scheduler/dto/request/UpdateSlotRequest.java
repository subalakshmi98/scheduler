package com.scheduler.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record UpdateSlotRequest(
        @NotNull
        LocalDateTime startTime,

        @NotNull
        LocalDateTime endTime
) {
}
