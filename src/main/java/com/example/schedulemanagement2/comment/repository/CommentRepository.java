package com.example.schedulemanagement2.comment.repository;

import com.example.schedulemanagement2.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 댓글 로그인한 유저id로 조회 + 일정 삭제 여부 확인
    List<Comment> findAllByUser_IdAndSchedule_Deleted(Long userId, boolean scheduleDeleted);

}
