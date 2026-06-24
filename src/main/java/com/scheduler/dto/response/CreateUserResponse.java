package com.scheduler.dto.response;
import java.util.UUID;

public record CreateUserResponse(
        UUID id,
        String name,
        String email
) {
}