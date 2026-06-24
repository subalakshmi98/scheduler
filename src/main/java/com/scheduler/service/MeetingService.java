package com.scheduler.service;

import com.scheduler.dto.request.CreateMeetingRequest;
import com.scheduler.dto.response.MeetingResponse;

public interface MeetingService {
    MeetingResponse createMeeting(String email, String slotNumber, CreateMeetingRequest request);
}
