package com.example.schedulemanagement2.schedule.entity;

import com.example.schedulemanagement2.comment.entity.Comment;
import com.example.schedulemanagement2.common.entity.BaseEntity;
import com.example.schedulemanagement2.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;

@SQLDelete(sql = "UPDATE schedules SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
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
    private boolean deleted = false;

    // users 테이블과 연관관계 설정
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 일정 조회 시 댓글 확인을 위해 양방향 설정
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
