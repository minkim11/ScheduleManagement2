package com.example.schedulemanagement2.user.controller;

import com.example.schedulemanagement2.user.dto.*;
import com.example.schedulemanagement2.user.service.UserService;
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

    @PostMapping("/users")
    public ResponseEntity<CreateUserResponse> createUser(
            @Valid @RequestBody CreateUserRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(request));
    }

    @GetMapping("/users")
    public ResponseEntity<List<ReadAllUsersResponse>> readAllUser() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAllUsers());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ReadOneUserResponse> readOneUser(
            @PathVariable Long id
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findOneUser(id));
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<UpdateUserResponse> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(id, request));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(
            @PathVariable Long id
    ) {
        userService.deleteUser(id);
        return new ResponseEntity<>("삭제되었습니다", HttpStatus.NO_CONTENT);
    }
}
