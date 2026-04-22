package com.example.schedulemanagement2.comment.service;

import com.example.schedulemanagement2.comment.dto.CreateCommentRequest;
import com.example.schedulemanagement2.comment.dto.CreateCommentResponse;
import com.example.schedulemanagement2.comment.dto.ReadAllCommentsResponse;
import com.example.schedulemanagement2.comment.entity.Comment;
import com.example.schedulemanagement2.comment.repository.CommentRepository;
import com.example.schedulemanagement2.common.exception.ScheduleNotFoundException;
import com.example.schedulemanagement2.common.exception.UserNotFoundException;
import com.example.schedulemanagement2.schedule.entity.Schedule;
import com.example.schedulemanagement2.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    // 댓글 생성
    @Transactional
    public CreateCommentResponse saveComment(Long scheduleId, Long userId, CreateCommentRequest request) {
        // 댓글 필드 저장 위해 유저, 일정 가져오기
        User user = commentRepository.findByUser_Id(userId).orElseThrow(
                () -> new UserNotFoundException("없는 유저")
        );
        Schedule schedule = commentRepository.findBySchedule_Id(scheduleId).orElseThrow(
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

    // 댓글 전체 조회
    @Transactional(readOnly = true)
    public List<ReadAllCommentsResponse> readAllComments(Long userId) {
        List<Comment> comments = commentRepository.findAllByUser_IdAndSchedule_Deleted(userId, false);
        return comments.stream()
                .map(comment -> new ReadAllCommentsResponse(
                    comment.getCommentId(),
                    comment.getSchedule().getId(),
                    comment.getComment()))
                .toList();
    }
}
