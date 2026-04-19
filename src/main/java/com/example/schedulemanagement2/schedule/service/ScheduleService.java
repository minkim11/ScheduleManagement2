package com.example.schedulemanagement2.schedule.service;

import com.example.schedulemanagement2.schedule.dto.CreateScheduleRequest;
import com.example.schedulemanagement2.schedule.dto.CreateScheduleResponse;
import com.example.schedulemanagement2.schedule.dto.ReadAllSchedulesResponse;
import com.example.schedulemanagement2.schedule.dto.ReadOneScheduleResponse;
import com.example.schedulemanagement2.schedule.entity.Schedule;
import com.example.schedulemanagement2.schedule.repository.ScheduleRepository;
import com.example.schedulemanagement2.user.entity.User;
import com.example.schedulemanagement2.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    @Transactional
    public CreateScheduleResponse saveSchedule(CreateScheduleRequest request, Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalStateException("없는 유저"));
        Schedule schedule = new Schedule(request.getTitle(), request.getDescription(), user);
        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new CreateScheduleResponse(
                savedSchedule.getId(),
                savedSchedule.getTitle(),
                savedSchedule.getDescription(),
                savedSchedule.getCreatedAt(),
                savedSchedule.getModifiedAt(),
                id
        );
    }

    @Transactional(readOnly = true)
    public List<ReadAllSchedulesResponse> readAllSchedules(Long id) {
        List<Schedule> schedules = scheduleRepository.findByUser_Id(id);
        return schedules.stream()
                .map(schedule -> new ReadAllSchedulesResponse(
                        schedule.getId(),
                        id,
                        schedule.getTitle()
                ))
                .toList();
    }

    @Transactional(readOnly = true)
    public ReadOneScheduleResponse readOneSchedule(Long userId, Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalStateException("없는 일정")
        );
        return new ReadOneScheduleResponse(
                schedule.getId(),
                userId,
                schedule.getTitle(),
                schedule.getDescription(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }
}
