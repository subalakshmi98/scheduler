package com.scheduler.dto.response;

import java.util.List;

public record UserAvailabilityResponse(
        List<SlotAvailabilityResponse> free,
        List<SlotAvailabilityResponse> busy
) {
}
