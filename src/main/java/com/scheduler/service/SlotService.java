package com.scheduler.service;

import com.scheduler.dto.request.CreateSlotRequest;
import com.scheduler.dto.request.UpdateSlotRequest;
import com.scheduler.dto.response.SlotResponse;
import com.scheduler.dto.response.SlotAvailabilityResponse;

import java.util.List;

public interface SlotService {
    SlotResponse createSlot(String email, CreateSlotRequest request);
    List<SlotAvailabilityResponse> getSlots(String email);
    SlotResponse updateSlot(String email, String slotNumber, UpdateSlotRequest request);
    void deleteSlot(String email, String slotNumber);
}
