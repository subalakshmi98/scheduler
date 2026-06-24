package com.scheduler.dto.request;
import jakarta.validation.constraints.NotBlank;

public record CreateUserRequest(
        String name,
        String email
) {
}