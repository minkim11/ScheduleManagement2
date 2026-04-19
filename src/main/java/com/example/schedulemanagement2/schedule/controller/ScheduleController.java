package com.example.schedulemanagement2.schedule.controller;

import com.example.schedulemanagement2.schedule.dto.CreateScheduleRequest;
import com.example.schedulemanagement2.schedule.dto.CreateScheduleResponse;
import com.example.schedulemanagement2.schedule.dto.ReadAllSchedulesResponse;
import com.example.schedulemanagement2.schedule.dto.ReadOneScheduleResponse;
import com.example.schedulemanagement2.schedule.service.ScheduleService;
import com.example.schedulemanagement2.user.dto.SessionUser;
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
            @RequestBody CreateScheduleRequest request,
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
}
