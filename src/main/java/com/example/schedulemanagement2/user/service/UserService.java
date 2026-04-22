package com.example.schedulemanagement2.user.service;

import com.example.schedulemanagement2.common.exception.EmailNotUniqueException;
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

    // 회원가입, 요청 받은 유저 정보 DB에 저장
    @Transactional
    public CreateUserResponse saveUser(CreateUserRequest request) {
        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        // 이메일 중복 시 예외 발생
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailNotUniqueException("이미 사용중인 이메일");
        }
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

    // 유저 전체 조회
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

    // 유저 단건 조회
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

    // 유저 수정 (유저이름 수정)
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

    // 유저 id 확인 후 유저 삭제
    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("없는 유저");
        }
        userRepository.deleteById(id);
    }

    // 로그인
    @Transactional(readOnly = true)
    public SessionUser login(LoginRequest request) {
        // 이메일로 유저 존재하는지 확인
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new UserNotFoundException("없는 유저")
        );
        // 비밀번호 검증, 예외 발생
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalStateException("비밀번호 불일치");
        }
        // dto 반환
        return new SessionUser(user.getId(), user.getEmail(), user.getName());
    }
}
