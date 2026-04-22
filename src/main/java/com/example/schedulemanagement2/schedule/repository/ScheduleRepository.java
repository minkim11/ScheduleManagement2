package com.example.schedulemanagement2.schedule.repository;

import com.example.schedulemanagement2.schedule.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    // List<Schedule> findByUser_Id(Long userId);

    Page<Schedule> findByUser_Id(Long userId, Pageable pageable);

}
