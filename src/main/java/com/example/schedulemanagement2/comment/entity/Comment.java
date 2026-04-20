package com.example.schedulemanagement2.comment.entity;

import com.example.schedulemanagement2.common.entity.BaseEntity;
import com.example.schedulemanagement2.schedule.entity.Schedule;
import com.example.schedulemanagement2.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "comments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    public Comment(String comment, Schedule schedule, User user) {
        this.comment = comment;
        this.schedule = schedule;
        this.user = user;
    }
}
