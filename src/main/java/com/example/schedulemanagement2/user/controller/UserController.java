package com.example.schedulemanagement2.user.controller;

import com.example.schedulemanagement2.user.dto.*;
import com.example.schedulemanagement2.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // 유저 생성(회원가입)
    @PostMapping("/users")
    public ResponseEntity<CreateUserResponse> createUser(
            @Valid @RequestBody CreateUserRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(request));
    }

    // 유저 전체 조회(운영자 입장에서 구현)
    @GetMapping("/users")
    public ResponseEntity<List<ReadAllUsersResponse>> readAllUser() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAllUsers());
    }
    // 유저 단건 조회(운영자 입장에서 구현)
    @GetMapping("/users/{id}")
    public ResponseEntity<ReadOneUserResponse> readOneUser(
            @PathVariable Long id
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findOneUser(id));
    }

    // 유저 수정(이름만 변경, 운영자 입장에서 구현)
    @PatchMapping("/users/{id}")
    public ResponseEntity<UpdateUserResponse> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(id, request));
    }

    // 유저 삭제(운영자 입장에서 구현)
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(
            @PathVariable Long id
    ) {
        userService.deleteUser(id);
        return new ResponseEntity<>("삭제되었습니다", HttpStatus.OK); // 클라이언트에게 메시지 표시위해 200 OK
    }

    // 로그인 기능
    @PostMapping("/login")
    public ResponseEntity<String> login(
            @Valid @RequestBody LoginRequest request,
            HttpSession session
    ){
        SessionUser sessionUser = userService.login(request);
        session.setAttribute("login", sessionUser);
        return ResponseEntity.status(HttpStatus.OK).body("로그인되었습니다");
    }
}
