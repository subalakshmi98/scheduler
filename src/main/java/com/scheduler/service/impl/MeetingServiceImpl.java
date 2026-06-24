package com.scheduler.service.impl;

import com.scheduler.constants.SlotStatus;
import com.scheduler.dto.request.CreateMeetingRequest;
import com.scheduler.dto.request.ParticipantRequest;
import com.scheduler.dto.response.MeetingResponse;
import com.scheduler.model.Meeting;
import com.scheduler.model.Participant;
import com.scheduler.model.Slot;
import com.scheduler.model.User;
import com.scheduler.repository.MeetingRepository;
import com.scheduler.repository.ParticipantRepository;
import com.scheduler.repository.SlotRepository;
import com.scheduler.service.MeetingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class MeetingServiceImpl implements MeetingService {

    private final MeetingRepository meetingRepository;
    private final ParticipantRepository participantRepository;
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

        Set<Participant> participants = new HashSet<>();

        if (request.participants() != null) {
            for (ParticipantRequest p : request.participants()) {

                Participant participant = Participant.builder()
                        .name(p.name())
                        .email(p.email())
                        .build();

                participants.add(participant);
            }

            participantRepository.saveAll(participants);
        }

        Meeting meeting = meetingRepository.save(Meeting.builder()
                .title(request.title())
                .description(request.description())
                .slot(slot)
                .participants(participants)
                .build()
        );

        slot.setStatus(SlotStatus.BOOKED);

        return new MeetingResponse(
                meeting.getTitle(),
                meeting.getDescription(),
                slot.getSlotNumber(),
                participants.stream()
                        .map(Participant::getEmail)
                        .toList()
        );
    }
}
