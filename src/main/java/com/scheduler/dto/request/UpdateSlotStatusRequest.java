package com.scheduler.dto.request;

import com.scheduler.constants.SlotStatus;

public record UpdateSlotStatusRequest(
        SlotStatus status
) {
}
