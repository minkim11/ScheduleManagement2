package com.example.schedulemanagement2.schedule.service;

import com.example.schedulemanagement2.comment.dto.ReadAllCommentsResponse;
import com.example.schedulemanagement2.comment.entity.Comment;
import com.example.schedulemanagement2.common.exception.ScheduleNotFoundException;
import com.example.schedulemanagement2.common.exception.UserNotFoundException;
import com.example.schedulemanagement2.common.exception.UserNotLoginException;
import com.example.schedulemanagement2.common.interfaces.UserCheck;
import com.example.schedulemanagement2.schedule.dto.*;
import com.example.schedulemanagement2.schedule.entity.Schedule;
import com.example.schedulemanagement2.schedule.repository.ScheduleRepository;
import com.example.schedulemanagement2.user.dto.SessionUser;
import com.example.schedulemanagement2.user.entity.User;
import com.example.schedulemanagement2.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService implements UserCheck {
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    // 일정 생성
    @Transactional
    public CreateScheduleResponse saveSchedule(CreateScheduleRequest request, SessionUser sessionUser) {
        userCheck(sessionUser); // 로그인 여부 확인
        User user = userRepository.findById(sessionUser.getId()).orElseThrow(() -> new UserNotFoundException("없는 유저"));
        Schedule schedule = new Schedule(request.getTitle(), request.getDescription(), user);
        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new CreateScheduleResponse(
                savedSchedule.getId(),
                savedSchedule.getTitle(),
                savedSchedule.getDescription(),
                savedSchedule.getCreatedAt(),
                savedSchedule.getModifiedAt(),
                sessionUser.getId()
        );
    }

    // 일정 전체 조회, 페이지네이션, 정렬
    @Transactional(readOnly = true)
    public List<ReadAllSchedulesResponse> readAllSchedules(SessionUser sessionUser, int pageNum, int pageSize) {
        userCheck(sessionUser); // 로그인 여부 확인
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
    public ReadOneScheduleResponse readOneSchedule(SessionUser sessionUser, Long scheduleId) {
        userCheck(sessionUser); // 로그인 여부 확인
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
                sessionUser.getId(),
                schedule.getTitle(),
                schedule.getDescription(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt(),
                dtos
        );
    }

    // 일정 수정
    @Transactional
    public UpdateScheduleResponse updateSchedule(Long scheduleId, SessionUser sessionUser, UpdateScheduleRequest request) {
        userCheck(sessionUser); // 로그인 여부 확인
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new ScheduleNotFoundException("없는 일정")
        );
        schedule.update(request.getTitle(), request.getDescription());
        return new UpdateScheduleResponse(
                schedule.getId(),
                sessionUser.getId(),
                schedule.getTitle(),
                schedule.getDescription(),
                schedule.getModifiedAt()
        );
    }

    // 일정 삭제
    @Transactional
    public void deleteSchedule(Long id, SessionUser sessionUser) {
        userCheck(sessionUser); // 로그인 여부 확인
        if (!scheduleRepository.existsById(id)) {
            throw new ScheduleNotFoundException("없는 일정");
        }
        scheduleRepository.deleteById(id);
    }

    // 로그인 여부 확인 후 예외 발생 메서드
    @Override
    public void userCheck(SessionUser sessionUser) {
        if (sessionUser == null) {
            throw new UserNotLoginException("로그인이 필요합니다");
        }
    }
}
