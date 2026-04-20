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

    @PostMapping("/schedules")
    public ResponseEntity<CreateScheduleResponse> createSchedule(
            @Valid @RequestBody CreateScheduleRequest request,
            @SessionAttribute(name = "login") SessionUser sessionUser
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.saveSchedule(request, sessionUser.getId()));
    }

    @GetMapping("/schedules")
    public ResponseEntity<List<ReadAllSchedulesResponse>> readAllSchedules(
            @SessionAttribute(name = "login") SessionUser sessionUser
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.readAllSchedules(sessionUser.getId()));
    }

    @GetMapping("/schedules/{id}")
    public ResponseEntity<ReadOneScheduleResponse> readOneSchedule(
            @SessionAttribute(name = "login") SessionUser sessionUser,
            @PathVariable Long id
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.readOneSchedule(sessionUser.getId(), id));
    }

    @PatchMapping("/schedules/{id}")
    public ResponseEntity<UpdateScheduleResponse> updateSchedule(
            @PathVariable Long id,
            @SessionAttribute(name = "login") SessionUser sessionUser,
            @Valid @RequestBody UpdateScheduleRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.updateSchedule(id, sessionUser.getId(), request));
    }

    @DeleteMapping("/schedules/{id}")
    public ResponseEntity<String> deleteSchedule(
            @PathVariable Long id,
            @SessionAttribute(name = "login") SessionUser sessionUser
    ) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("삭제되었습니다");
    }
}
