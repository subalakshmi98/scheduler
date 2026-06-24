package com.scheduler.service;

import com.scheduler.dto.request.CreateUserRequest;
import com.scheduler.dto.response.CreateUserResponse;

public interface UserService {
    CreateUserResponse create(CreateUserRequest request);
}
