package com.scheduler.dto.request;

import com.scheduler.constants.SlotStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateSlotStatusRequest(
        @NotNull
        SlotStatus status
) {
}
