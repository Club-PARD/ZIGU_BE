package com.pard.gz.zigu.user.entity;

import com.pard.gz.zigu.borrowed.entity.Borrowed;
import com.pard.gz.zigu.post.entity.Post;
import com.pard.gz.zigu.school.entity.School;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    // user_id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 닉네임
    @Column(name = "nickname", nullable = false)
    private String nickname;

    // 비밀번호, 해싱하여 저장할 것
    @Column(name = "password", nullable = false)
    private String password;

    // 유저의 학교, 학교 이름 필요
    @ManyToOne
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    // 학생 메일(중복 불가. unique)
    @Column(unique = true, nullable = false)
    private String studentMail;

    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL, orphanRemoval = true)
    // 해당 user가 작성한 게시물
    private List<Post> postList = new ArrayList<>();

    // 내가 빌린 모든 기록
    @OneToMany(mappedBy = "borrower", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Borrowed> borrowedList = new ArrayList<>();
}
