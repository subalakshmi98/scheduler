package com.scheduler.service.impl;

import com.scheduler.dto.request.CreateUserRequest;
import com.scheduler.dto.response.CreateUserResponse;
import com.scheduler.model.Calendar;
import com.scheduler.model.User;
import com.scheduler.repository.CalendarRepository;
import com.scheduler.repository.UserRepository;
import com.scheduler.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CalendarRepository calendarRepository;

    public CreateUserResponse create(CreateUserRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Email already exists");
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
}