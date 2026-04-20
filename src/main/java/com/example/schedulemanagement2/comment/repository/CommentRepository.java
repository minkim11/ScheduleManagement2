package com.example.schedulemanagement2.comment.repository;

import com.example.schedulemanagement2.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
