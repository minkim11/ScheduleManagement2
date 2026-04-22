package com.example.schedulemanagement2.schedule.controller;

import com.example.schedulemanagement2.schedule.dto.*;
import com.example.schedulemanagement2.schedule.service.ScheduleService;
import com.example.schedulemanagement2.user.dto.SessionUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    // 일정 생성 (로그인 시 가능)
    @PostMapping("/schedules")
    public ResponseEntity<CreateScheduleResponse> createSchedule(
            @Valid @RequestBody CreateScheduleRequest request,
            @SessionAttribute(name = "login", required = false) SessionUser sessionUser
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.saveSchedule(request, sessionUser));
    }

    // 일정 전체 조회, 페이지네이션 구현 - 쿼리 파라미터 미입력 시 페이지 크기 10, 페이지 번호 0 (로그인 시 가능)
    @GetMapping("/schedules")
    public ResponseEntity<List<ReadAllSchedulesResponse>> readAllSchedules(
            @SessionAttribute(name = "login", required = false) SessionUser sessionUser,
            @RequestParam(name = "page-num", defaultValue = "0") int pageNum,
            @RequestParam(name = "page-size", defaultValue = "10") int pageSize
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.readAllSchedules(sessionUser, pageNum, pageSize));
    }

    // 일정 단건 조회 (로그인 시 가능, 해당 일정 댓글(Comment)도 조회)
    @GetMapping("/schedules/{id}")
    public ResponseEntity<ReadOneScheduleResponse> readOneSchedule(
            @SessionAttribute(name = "login", required = false) SessionUser sessionUser,
            @PathVariable Long id
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.readOneSchedule(sessionUser, id));
    }

    // 일정 수정 (로그인 시 가능)
    @PatchMapping("/schedules/{id}")
    public ResponseEntity<UpdateScheduleResponse> updateSchedule(
            @PathVariable Long id,
            @SessionAttribute(name = "login", required = false) SessionUser sessionUser,
            @Valid @RequestBody UpdateScheduleRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.updateSchedule(id, sessionUser, request));
    }

    // 일정 삭제(로그인 시 가능)
    @DeleteMapping("/schedules/{id}")
    public ResponseEntity<String> deleteSchedule(
            @PathVariable Long id,
            @SessionAttribute(name = "login", required = false) SessionUser sessionUser
    ) {
        scheduleService.deleteSchedule(id, sessionUser);
        return ResponseEntity.status(HttpStatus.OK).body("삭제되었습니다");
    }


}
