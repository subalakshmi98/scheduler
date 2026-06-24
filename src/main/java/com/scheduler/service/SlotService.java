package com.scheduler.service;

import com.scheduler.dto.request.CreateSlotRequest;
import com.scheduler.dto.response.CreateSlotResponse;
import com.scheduler.dto.response.SlotAvailabilityResponse;

import java.util.List;

public interface SlotService {
    CreateSlotResponse createSlot(String email, CreateSlotRequest request);
    List<SlotAvailabilityResponse> getSlots(String email);
}
