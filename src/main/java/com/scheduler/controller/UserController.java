package com.scheduler.controller;

import com.scheduler.dto.request.CreateUserRequest;
import com.scheduler.dto.response.CreateUserResponse;
import com.scheduler.dto.response.UserAvailabilityResponse;
import com.scheduler.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @PostMapping
    public ResponseEntity<CreateUserResponse> create(@RequestBody @Valid CreateUserRequest request) {
        CreateUserResponse response = userService.create(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{email}/availability")
    public ResponseEntity<UserAvailabilityResponse> getAvailability(@PathVariable String email,
                                                    @RequestParam LocalDateTime from, @RequestParam LocalDateTime to) {
        UserAvailabilityResponse response = userService.getAvailability(email, from, to);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}