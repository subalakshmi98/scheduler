package com.scheduler.controller;

import com.scheduler.dto.request.CreateSlotRequest;
import com.scheduler.dto.request.UpdateSlotRequest;
import com.scheduler.dto.request.UpdateSlotStatusRequest;
import com.scheduler.dto.response.SlotResponse;
import com.scheduler.dto.response.SlotAvailabilityResponse;
import com.scheduler.service.SlotService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users/{email}/slots")
public class SlotController {
    private final SlotService slotService;

    @PostMapping
    public ResponseEntity<SlotResponse> createSlot(@PathVariable String email,
                                                  @Valid @RequestBody CreateSlotRequest request) {

        SlotResponse response = slotService.createSlot(email, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<List<SlotAvailabilityResponse>> getSlots(@PathVariable String email) {
        List<SlotAvailabilityResponse> response = slotService.getSlots(email);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{slotNumber}")
    public ResponseEntity<SlotResponse> updateSlot(@PathVariable String email,
                                   @PathVariable String slotNumber,
                                   @Valid @RequestBody UpdateSlotRequest request) {

        SlotResponse response = slotService.updateSlot(email, slotNumber, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{slotNumber}")
    public ResponseEntity<String> deleteSlot(@PathVariable String email, @PathVariable String slotNumber) {
        slotService.deleteSlot(email, slotNumber);
        return ResponseEntity.status(HttpStatus.OK).body("Slot Deleted");
    }

    @PatchMapping("/{slotNumber}")
    public ResponseEntity<SlotResponse> updateStatus(@PathVariable String email, @PathVariable String slotNumber,
                                     @Valid @RequestBody UpdateSlotStatusRequest request) {

        SlotResponse response = slotService.updateStatus(email, slotNumber, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
