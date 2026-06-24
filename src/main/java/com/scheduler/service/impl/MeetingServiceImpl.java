package com.scheduler.service.impl;

import com.scheduler.constants.SlotStatus;
import com.scheduler.dto.request.CreateMeetingRequest;
import com.scheduler.dto.response.MeetingResponse;
import com.scheduler.model.Meeting;
import com.scheduler.model.Slot;
import com.scheduler.repository.MeetingRepository;
import com.scheduler.repository.SlotRepository;
import com.scheduler.service.MeetingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MeetingServiceImpl implements MeetingService {

    private final MeetingRepository meetingRepository;
    private final SlotRepository slotRepository;

    @Override
    public MeetingResponse createMeeting(String email, String slotNumber, CreateMeetingRequest request) {

        Slot slot = slotRepository.findByOwnerEmailAndSlotNumber(email, slotNumber)
                .orElseThrow(() -> new RuntimeException("Slot not found"));

        if (slot.getStatus() != SlotStatus.FREE ) {
            throw new RuntimeException("Only FREE slots can be booked");
        }

        if (request.title() == null || request.title().isBlank()) {
            throw new RuntimeException("Meeting title is required");
        }

        Meeting meeting = meetingRepository.save(Meeting.builder()
                .title(request.title())
                .description(request.description())
                .slot(slot)
                .build()
        );

        slot.setStatus(SlotStatus.BOOKED);

        return new MeetingResponse(
                meeting.getTitle(),
                meeting.getDescription(),
                slot.getSlotNumber()
        );
    }
}
