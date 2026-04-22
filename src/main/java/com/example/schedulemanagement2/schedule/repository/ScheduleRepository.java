package com.example.schedulemanagement2.schedule.repository;

import com.example.schedulemanagement2.schedule.entity.Schedule;
import com.example.schedulemanagement2.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    // List<Schedule> findByUser_Id(Long userId);

    Page<Schedule> findByUser_Id(Long userId, Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.id = :userId")
    Optional<User> findByUser_Id(Long userId);
}
