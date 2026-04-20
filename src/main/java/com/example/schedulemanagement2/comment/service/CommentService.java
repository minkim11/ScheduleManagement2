package com.example.schedulemanagement2.comment.service;

import com.example.schedulemanagement2.comment.dto.CreateCommentRequest;
import com.example.schedulemanagement2.comment.dto.CreateCommentResponse;
import com.example.schedulemanagement2.comment.entity.Comment;
import com.example.schedulemanagement2.comment.repository.CommentRepository;
import com.example.schedulemanagement2.common.exception.ScheduleNotFoundException;
import com.example.schedulemanagement2.common.exception.UserNotLoginException;
import com.example.schedulemanagement2.schedule.entity.Schedule;
import com.example.schedulemanagement2.schedule.repository.ScheduleRepository;
import com.example.schedulemanagement2.schedule.service.ScheduleService;
import com.example.schedulemanagement2.user.dto.SessionUser;
import com.example.schedulemanagement2.user.entity.User;
import com.example.schedulemanagement2.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    @Transactional
    public CreateCommentResponse saveComment(Long scheduleId, Long userId, CreateCommentRequest request) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotLoginException("로그인이 필요합니다")
        );
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new ScheduleNotFoundException("없는 일정")
        );
        Comment comment = new Comment(request.getComment(), schedule, user);
        Comment savedComment = commentRepository.save(comment);
        return new CreateCommentResponse(
                savedComment.getCommentId(),
                scheduleId,
                savedComment.getComment(),
                savedComment.getCreatedAt(),
                savedComment.getModifiedAt()
        );
    }
}
