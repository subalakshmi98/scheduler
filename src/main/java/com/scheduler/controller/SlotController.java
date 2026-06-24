package com.scheduler.controller;

import com.scheduler.dto.request.CreateSlotRequest;
import com.scheduler.dto.request.UpdateSlotRequest;
import com.scheduler.dto.request.UpdateSlotStatusRequest;
import com.scheduler.dto.response.SlotResponse;
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
    public SlotResponse createSlot(@PathVariable String email,
                                   @RequestBody CreateSlotRequest request) {

        return slotService.createSlot(email, request);
    }

    @GetMapping
    public List<SlotAvailabilityResponse> getSlots(@PathVariable String email) {
        return slotService.getSlots(email);
    }

    @PutMapping("/{slotNumber}")
    public SlotResponse updateSlot(@PathVariable String email,
                                   @PathVariable String slotNumber,
                                   @RequestBody UpdateSlotRequest request) {

        return slotService.updateSlot(email, slotNumber, request);
    }

    @DeleteMapping("/{slotNumber}")
    public void deleteSlot(@PathVariable String email, @PathVariable String slotNumber) {
        slotService.deleteSlot(email, slotNumber);
    }

    @PatchMapping("/{slotNumber}")
    public SlotResponse updateStatus(@PathVariable String email, @PathVariable String slotNumber,
                                     @RequestBody UpdateSlotStatusRequest request) {

        return slotService.updateStatus(email, slotNumber, request);
    }
}
