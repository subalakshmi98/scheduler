package com.scheduler.unitTest;

import com.scheduler.constants.SlotStatus;
import com.scheduler.dto.request.CreateMeetingRequest;
import com.scheduler.dto.response.MeetingResponse;
import com.scheduler.model.Meeting;
import com.scheduler.model.Slot;
import com.scheduler.repository.*;
import com.scheduler.service.MeetingService;
import com.scheduler.service.impl.MeetingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MeetingServiceTest {

    @Mock
    private SlotRepository slotRepository;

    @Mock
    private MeetingRepository meetingRepository;

    @Mock
    private ParticipantRepository participantRepository;

    private MeetingService meetingService;

    @BeforeEach
    void setup() {
        meetingService = new MeetingServiceImpl(
                meetingRepository,
                participantRepository,
                slotRepository);
    }

    @Test
    void shouldCreateMeeting() {

        String email = "alex@test.com";

        Slot slot = Slot.builder()
                .slotNumber("S1")
                .status(SlotStatus.FREE)
                .build();

        when(slotRepository.findByOwnerEmailAndSlotNumber(email, "S1"))
                .thenReturn(Optional.of(slot));

        CreateMeetingRequest request = new CreateMeetingRequest(
                        "Technical Interview",
                        "Spring Boot discussion",
                        List.of()
        );

        when(meetingRepository.save(any(Meeting.class))).thenAnswer(invocation -> invocation.getArgument(0));
        MeetingResponse response = meetingService.createMeeting(email, "S1", request);
        assertEquals("Technical Interview", response.title());
        assertEquals(SlotStatus.BOOKED, slot.getStatus());
        verify(meetingRepository).save(any(Meeting.class));
    }
}
