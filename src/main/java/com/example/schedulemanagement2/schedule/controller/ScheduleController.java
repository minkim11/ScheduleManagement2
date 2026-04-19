package com.example.schedulemanagement2.schedule.controller;

import com.example.schedulemanagement2.schedule.dto.CreateScheduleRequest;
import com.example.schedulemanagement2.schedule.dto.CreateScheduleResponse;
import com.example.schedulemanagement2.schedule.service.ScheduleService;
import com.example.schedulemanagement2.user.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

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
}
