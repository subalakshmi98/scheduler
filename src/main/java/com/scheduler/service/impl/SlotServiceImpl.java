package com.scheduler.service.impl;

import com.scheduler.constants.SlotStatus;
import com.scheduler.dto.request.CreateSlotRequest;
import com.scheduler.dto.request.UpdateSlotRequest;
import com.scheduler.dto.request.UpdateSlotStatusRequest;
import com.scheduler.dto.response.SlotResponse;
import com.scheduler.dto.response.SlotAvailabilityResponse;
import com.scheduler.model.Slot;
import com.scheduler.model.User;
import com.scheduler.repository.SlotRepository;
import com.scheduler.repository.UserRepository;
import com.scheduler.service.SlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SlotServiceImpl implements SlotService {

    private final SlotRepository slotRepository;
    private final UserRepository userRepository;

    @Override
    public SlotResponse createSlot(String email, CreateSlotRequest request) {

        User owner = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!request.endTime().isAfter(request.startTime())) {
            throw new RuntimeException("End time must be after start time");
        }

        boolean overlap = slotRepository.existsOverlappingSlot(
                email,
                request.startTime(),
                request.endTime()
        );

        if (overlap) {
            throw new RuntimeException("Slot overlaps existing slot");
        }

        long nextSlotNumber = slotRepository.countByOwnerEmail(email) + 1;
        Slot slot = slotRepository.save(Slot.builder()
                .slotNumber("S" + nextSlotNumber)
                .owner(owner)
                .startTime(request.startTime())
                .endTime(request.endTime())
                .status(SlotStatus.FREE)
                .build()
        );

        return new SlotResponse(
                slot.getSlotNumber(),
                slot.getStartTime(),
                slot.getEndTime(),
                slot.getStatus()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<SlotAvailabilityResponse> getSlots(String email) {

        List<Slot> slots = slotRepository.findByOwnerEmailOrderByStartTimeAsc(email);
        return slots.stream()
                .map(slot -> new SlotAvailabilityResponse(
                        slot.getSlotNumber(),
                        slot.getId(),
                        slot.getStartTime(),
                        slot.getEndTime(),
                        slot.getStatus()
                        )
                ).toList();
    }

    @Override
    public SlotResponse updateSlot(String email, String slotNumber, UpdateSlotRequest request) {

        Slot slot = slotRepository.findByOwnerEmailAndSlotNumber(email, slotNumber)
                .orElseThrow(() -> new RuntimeException("Slot not found"));

        if (!request.endTime().isAfter(request.startTime())) {
            throw new RuntimeException("End time must be after start time");
        }

        if (slot.getStatus() == SlotStatus.BOOKED) {
            throw new RuntimeException("Booked slot cannot be modified");
        }

        boolean overlap = slotRepository.existsOverlappingSlotExcludingCurrent(
                slotNumber,
                slot.getOwner().getEmail(),
                request.startTime(),
                request.endTime()
        );

        if (overlap) {
            throw new RuntimeException("Slot overlaps existing slot");
        }

        slot.setStartTime(request.startTime());
        slot.setEndTime(request.endTime());

        return new SlotResponse(
                slot.getSlotNumber(),
                slot.getStartTime(),
                slot.getEndTime(),
                slot.getStatus()
        );
    }

    @Override
    public void deleteSlot(String email, String slotNumber) {

        Slot slot = slotRepository.findByOwnerEmailAndSlotNumber(email, slotNumber)
                .orElseThrow(() -> new RuntimeException("Slot not found"));

        if (slot.getStatus() == SlotStatus.BOOKED) {
            throw new RuntimeException("Booked slot cannot be deleted");
        }

        slotRepository.delete(slot);
    }

    @Override
    public SlotResponse updateStatus(String email, String slotNumber, UpdateSlotStatusRequest request) {

        Slot slot = slotRepository.findByOwnerEmailAndSlotNumber(email, slotNumber)
                .orElseThrow(() -> new RuntimeException("Slot not found"));

        if (slot.getStatus() == SlotStatus.BOOKED) {
            throw new RuntimeException("Booked slot cannot be modified");
        }

        slot.setStatus(request.status());

        return new SlotResponse(
                slot.getSlotNumber(),
                slot.getStartTime(),
                slot.getEndTime(),
                slot.getStatus()
        );
    }
}
