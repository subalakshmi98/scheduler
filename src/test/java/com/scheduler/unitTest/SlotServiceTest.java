package com.scheduler.unitTest;

import com.scheduler.constants.SlotStatus;
import com.scheduler.dto.request.CreateSlotRequest;
import com.scheduler.dto.response.SlotResponse;
import com.scheduler.model.Slot;
import com.scheduler.model.User;
import com.scheduler.repository.CalendarRepository;
import com.scheduler.repository.SlotRepository;
import com.scheduler.repository.UserRepository;
import com.scheduler.service.SlotService;
import com.scheduler.service.UserService;
import com.scheduler.service.impl.SlotServiceImpl;
import com.scheduler.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SlotServiceTest {

    @Mock
    private SlotRepository slotRepository;

    @Mock
    private UserRepository userRepository;

    private SlotService slotService;

    @BeforeEach
    void setup() {
        slotService = new SlotServiceImpl(
                slotRepository,
                userRepository);
    }

    @Test
    void shouldCreateSlot() {

        String email = "alex@test.com";

        User user = User.builder()
                .id(UUID.randomUUID())
                .email(email)
                .name("Alex")
                .build();

        CreateSlotRequest request = new CreateSlotRequest(
                        LocalDateTime.of(2026, 6, 21, 9, 0),
                        LocalDateTime.of(2026, 6, 21, 10, 0)
        );

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(slotRepository.countByOwnerEmail(email)).thenReturn(0L);
        when(slotRepository.existsOverlappingSlot(any(), any(), any())).thenReturn(false);
        when(slotRepository.save(any(Slot.class))).thenAnswer(invocation -> invocation.getArgument(0));

        SlotResponse response = slotService.createSlot(email, request);

        assertEquals("S1", response.slotNumber());
        assertEquals(SlotStatus.FREE, response.status());

        verify(slotRepository).save(any(Slot.class));
    }
}
