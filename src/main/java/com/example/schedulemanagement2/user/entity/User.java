package com.example.schedulemanagement2.user.entity;

import com.example.schedulemanagement2.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

// 일정과 댓글이 있을 시 유저 삭제에서 예외 발생해서 soft delete 구현
@SQLDelete(sql = "UPDATE users SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true) //이메일 중복 못하게 설정, 중복이면 로그인 기능에서 이메일로 유저 찾을 때 예외 발생
    private String email;
    private String password;
    private boolean deleted = false;


    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public void update(String name) {
        this.name = name;
    }
}
