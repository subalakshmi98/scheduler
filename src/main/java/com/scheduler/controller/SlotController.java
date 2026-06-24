package com.scheduler.controller;

import com.scheduler.dto.request.CreateSlotRequest;
import com.scheduler.dto.response.CreateSlotResponse;
import com.scheduler.dto.response.SlotAvailabilityResponse;
import com.scheduler.service.SlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users/{email}/slots")
public class SlotController {
    private final SlotService slotService;

    @PostMapping
    public CreateSlotResponse createSlot(@PathVariable String email,
                                         @RequestBody CreateSlotRequest request) {

        return slotService.createSlot(email, request);
    }

    @GetMapping
    public List<SlotAvailabilityResponse> getSlots(@PathVariable String email) {
        return slotService.getSlots(email);
    }
}
