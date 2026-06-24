package com.scheduler.service;

import com.scheduler.dto.request.CreateUserRequest;
import com.scheduler.dto.response.CreateUserResponse;
import com.scheduler.dto.response.UserAvailabilityResponse;

import java.time.LocalDateTime;

public interface UserService {
    CreateUserResponse create(CreateUserRequest request);
    UserAvailabilityResponse getAvailability(String email, LocalDateTime from, LocalDateTime to);
}
