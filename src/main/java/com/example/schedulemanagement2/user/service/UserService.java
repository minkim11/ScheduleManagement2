package com.example.schedulemanagement2.user.service;

import com.example.schedulemanagement2.common.exception.UserNotFoundException;
import com.example.schedulemanagement2.user.config.PasswordEncoder;
import com.example.schedulemanagement2.user.dto.*;
import com.example.schedulemanagement2.user.entity.User;
import com.example.schedulemanagement2.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public CreateUserResponse saveUser(CreateUserRequest request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = new User(
                request.getName(),
                request.getEmail(),
                encodedPassword);
        User savedUser = userRepository.save(user);
        return new CreateUserResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getCreatedAt(),
                savedUser.getModifiedAt());
    }

    @Transactional(readOnly = true)
    public List<ReadAllUsersResponse> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> new ReadAllUsersResponse(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getCreatedAt(),
                        user.getModifiedAt()
                ))
                .toList();
    }

    @Transactional(readOnly = true)
    public ReadOneUserResponse findOneUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("없는 유저")
        );
        return new ReadOneUserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getModifiedAt());
    }

    @Transactional
    public UpdateUserResponse updateUser(Long id, UpdateUserRequest request) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("없는 유저")
        );
        user.update(request.getName());
        return new UpdateUserResponse(
                user.getId(),
                user.getName(),
                user.getModifiedAt()
        );
    }

    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("없는 유저");
        }
        userRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public SessionUser login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new UserNotFoundException("없는 유저")
        );
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalStateException("비밀번호 불일치");
        }
        return new SessionUser(user.getId(), user.getEmail());
    }
}
