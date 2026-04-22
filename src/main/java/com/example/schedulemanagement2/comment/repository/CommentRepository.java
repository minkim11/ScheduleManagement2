package com.example.schedulemanagement2.comment.repository;

import com.example.schedulemanagement2.comment.entity.Comment;
import com.example.schedulemanagement2.schedule.entity.Schedule;
import com.example.schedulemanagement2.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 댓글 로그인한 유저id로 조회 + 일정 삭제 여부 확인
    List<Comment> findAllByUser_IdAndSchedule_Deleted(Long userId, boolean scheduleDeleted);
    // 댓글 생성 시 유저, 일정 요구되므로 DB에서 가져오기
    @Query("SELECT u FROM User u WHERE u.id = :userId")
    Optional<User> findByUser_Id(Long userId);


    @Query("SELECT s FROM Schedule s WHERE s.id = :scheduleId")
    Optional<Schedule> findBySchedule_Id(Long scheduleId);
}
