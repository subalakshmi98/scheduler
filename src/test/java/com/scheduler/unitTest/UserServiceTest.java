package com.scheduler.unitTest;

import com.scheduler.dto.request.CreateUserRequest;
import com.scheduler.dto.response.CreateUserResponse;
import com.scheduler.model.Calendar;
import com.scheduler.model.User;
import com.scheduler.repository.CalendarRepository;
import com.scheduler.repository.MeetingRepository;
import com.scheduler.repository.SlotRepository;
import com.scheduler.repository.UserRepository;
import com.scheduler.service.UserService;
import com.scheduler.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SlotRepository slotRepository;

    @Mock
    private CalendarRepository calendarRepository;

    private UserService userService;

    @BeforeEach
    void setup() {
        userService = new UserServiceImpl(
                userRepository,
                calendarRepository,
                slotRepository);
    }

    @Test
    void shouldCreateUser() {

        CreateUserRequest request = new CreateUserRequest(
                "Alex",
                "alex@test.com"
        );

        User savedUser = User.builder()
                .id(UUID.randomUUID())
                .name("Alex")
                .email("alex@test.com")
                .build();

        when(userRepository.existsByEmail("alex@test.com")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(calendarRepository.save(any(Calendar.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CreateUserResponse response = userService.create(request);

        assertEquals("Alex", response.name());
        verify(userRepository).save(any(User.class));
        verify(calendarRepository).save(any(Calendar.class));
    }
}