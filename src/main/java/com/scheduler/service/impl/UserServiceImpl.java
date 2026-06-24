package com.scheduler.service.impl;

import com.scheduler.constants.SlotStatus;
import com.scheduler.dto.request.CreateUserRequest;
import com.scheduler.dto.response.CreateUserResponse;
import com.scheduler.dto.response.SlotAvailabilityResponse;
import com.scheduler.dto.response.UserAvailabilityResponse;
import com.scheduler.exception.EmailAlreadyExistsException;
import com.scheduler.exception.UserNotFoundException;
import com.scheduler.model.Calendar;
import com.scheduler.model.Slot;
import com.scheduler.model.User;
import com.scheduler.repository.CalendarRepository;
import com.scheduler.repository.SlotRepository;
import com.scheduler.repository.UserRepository;
import com.scheduler.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CalendarRepository calendarRepository;
    private final SlotRepository slotRepository;

    @Override
    public CreateUserResponse create(CreateUserRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyExistsException(request.email());
        }

        User user = userRepository.save(User.builder()
                .name(request.name())
                .email(request.email())
                .build()
        );

       calendarRepository.save(Calendar.builder()
               .owner(user)
               .build()
       );

       return new CreateUserResponse(
               user.getId(),
               user.getName(),
               user.getEmail()
       );
    }

    @Override
    @Transactional(readOnly = true)
    public UserAvailabilityResponse getAvailability(String email, LocalDateTime from, LocalDateTime to) {

        userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        List<Slot> slots = slotRepository.findAvailability(email, from, to);

        List<SlotAvailabilityResponse> free = slots.stream()
                .filter(slot ->
                        slot.getStatus() == SlotStatus.FREE)
                .map(this::mapToAvailability)
                .toList();

        List<SlotAvailabilityResponse> busy = slots.stream()
                .filter(slot ->
                        slot.getStatus() == SlotStatus.BUSY
                                || slot.getStatus() == SlotStatus.BOOKED)
                .map(this::mapToAvailability)
                .toList();

        return new UserAvailabilityResponse(
                free,
                busy
        );
    }

    private SlotAvailabilityResponse mapToAvailability(Slot slot) {

        String meetingTitle = null;

        if (slot.getMeeting() != null) {
            meetingTitle = slot.getMeeting().getTitle();
        }

        return new SlotAvailabilityResponse(
                slot.getSlotNumber(),
                slot.getId(),
                slot.getStartTime(),
                slot.getEndTime(),
                slot.getStatus(),
                meetingTitle
        );
    }
}