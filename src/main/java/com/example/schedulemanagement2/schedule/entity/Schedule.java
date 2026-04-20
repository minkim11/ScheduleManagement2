package com.example.schedulemanagement2.schedule.entity;

import com.example.schedulemanagement2.comment.entity.Comment;
import com.example.schedulemanagement2.common.entity.BaseEntity;
import com.example.schedulemanagement2.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "schedules")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "schedule")
    private final List<Comment> comments = new ArrayList<>();


    public Schedule(String title, String description, User user) {
        this.title = title;
        this.description = description;
        this.user = user;
    }

    public void update(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
