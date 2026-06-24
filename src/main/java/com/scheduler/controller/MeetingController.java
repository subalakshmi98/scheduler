package com.scheduler.controller;

import com.scheduler.dto.request.CreateMeetingRequest;
import com.scheduler.dto.response.MeetingResponse;
import com.scheduler.service.MeetingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users/{email}/slots/{slotNumber}")
public class MeetingController {

    private final MeetingService meetingService;

    @PostMapping("/meeting")
    public ResponseEntity<MeetingResponse> createMeeting(@PathVariable String email, @PathVariable String slotNumber,
                                                        @Valid @RequestBody CreateMeetingRequest request) {

        MeetingResponse response = meetingService.createMeeting(email, slotNumber, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
