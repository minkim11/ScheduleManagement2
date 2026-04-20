package com.example.schedulemanagement2.comment.repository;

import com.example.schedulemanagement2.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByUser_Id(Long userId);
}
