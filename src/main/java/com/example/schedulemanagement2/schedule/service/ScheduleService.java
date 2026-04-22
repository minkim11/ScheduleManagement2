package com.example.schedulemanagement2.schedule.service;

import com.example.schedulemanagement2.comment.dto.ReadAllCommentsResponse;
import com.example.schedulemanagement2.comment.entity.Comment;
import com.example.schedulemanagement2.common.exception.ScheduleNotFoundException;
import com.example.schedulemanagement2.common.exception.UserNotFoundException;
import com.example.schedulemanagement2.schedule.dto.*;
import com.example.schedulemanagement2.schedule.entity.Schedule;
import com.example.schedulemanagement2.schedule.repository.ScheduleRepository;
import com.example.schedulemanagement2.user.dto.SessionUser;
import com.example.schedulemanagement2.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    // 일정 생성
    @Transactional
    public CreateScheduleResponse saveSchedule(CreateScheduleRequest request, Long id) {
        User user = scheduleRepository.findByUser_Id(id).orElseThrow(() -> new UserNotFoundException("없는 유저"));
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

    // 일정 전체 조회, 페이지네이션, 정렬
    @Transactional(readOnly = true)
    public List<ReadAllSchedulesResponse> readAllSchedules(SessionUser sessionUser, int pageNum, int pageSize) {
        // 일정 페이지 생성
        Page<Schedule> schedules = scheduleRepository
                .findByUser_Id(
                        sessionUser.getId(), // 로그인한 유저 id
                        // 쿼리 파라미터로 받은 페이지 번호, 페이지 크기, 수정일 기준 내림차순 정렬
                        PageRequest.of(pageNum, pageSize, Sort.by("modifiedAt").descending()));
        // Page 스트림 사용하여 DTO 리스트로 반환
        return schedules.stream()
                .map(schedule -> new ReadAllSchedulesResponse(
                        schedule.getId(),
                        sessionUser.getName(),
                        schedule.getTitle(),
                        schedule.getDescription(),
                        schedule.getComments().size(),
                        schedule.getCreatedAt(),
                        schedule.getModifiedAt()
                ))
                .toList();
    }

    // 일정 단건 조회
    @Transactional(readOnly = true)
    public ReadOneScheduleResponse readOneSchedule(Long userId, Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new ScheduleNotFoundException("없는 일정")
        );
        // 댓글 엔티티 리스트 가져온 후 dto로 변환
        List<Comment> comments = schedule.getComments();
        List<ReadAllCommentsResponse> dtos = comments
                .stream()
                .map(comment -> new ReadAllCommentsResponse(
                        comment.getCommentId(),
                        scheduleId,
                        comment.getComment()
                ))
                .toList();
        return new ReadOneScheduleResponse(
                schedule.getId(),
                userId,
                schedule.getTitle(),
                schedule.getDescription(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt(),
                dtos
        );
    }

    // 일정 수정
    @Transactional
    public UpdateScheduleResponse updateSchedule(Long scheduleId, Long userId, UpdateScheduleRequest request) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new ScheduleNotFoundException("없는 일정")
        );
        schedule.update(request.getTitle(), request.getDescription());
        return new UpdateScheduleResponse(
                schedule.getId(),
                userId,
                schedule.getTitle(),
                schedule.getDescription(),
                schedule.getModifiedAt()
        );
    }

    // 일정 삭제
    @Transactional
    public void deleteSchedule(Long id) {
        if (!scheduleRepository.existsById(id)) {
            throw new ScheduleNotFoundException("없는 일정");
        }
        scheduleRepository.deleteById(id);
    }
}
