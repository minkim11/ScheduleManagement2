package com.example.schedulemanagement2.schedule.repository;

import com.example.schedulemanagement2.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
