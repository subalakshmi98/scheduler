package com.scheduler.controller;

import com.scheduler.dto.request.CreateUserRequest;
import com.scheduler.dto.response.CreateUserResponse;
import com.scheduler.dto.response.UserAvailabilityResponse;
import com.scheduler.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @PostMapping
    public CreateUserResponse create(@RequestBody @Valid CreateUserRequest request) {
        return userService.create(request);
    }

    @GetMapping("/{email}/availability")
    public UserAvailabilityResponse getAvailability(@PathVariable String email,
                                                    @RequestParam LocalDateTime from, @RequestParam LocalDateTime to) {
        return userService.getAvailability(email, from, to);
    }
}