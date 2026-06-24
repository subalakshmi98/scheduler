package com.scheduler.controller;

import com.scheduler.dto.request.CreateMeetingRequest;
import com.scheduler.dto.response.MeetingResponse;
import com.scheduler.service.MeetingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users/{email}/slots/{slotNumber}")
public class MeetingController {

    private final MeetingService meetingService;

    @PostMapping("/meeting")
    public MeetingResponse createMeeting(@PathVariable String email, @PathVariable String slotNumber,
                                         @Valid @RequestBody CreateMeetingRequest request) {

        return meetingService.createMeeting(email, slotNumber, request);
    }
}
