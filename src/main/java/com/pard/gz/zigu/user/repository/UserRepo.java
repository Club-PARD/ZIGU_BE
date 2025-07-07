package com.pard.gz.zigu.user.repository;

import com.pard.gz.zigu.post.entity.Post;
import com.pard.gz.zigu.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    boolean existsByStudentMail(String studentMail); // 해당 studentMail을 가진 user가 DB에 존재하는지

    Optional<User> findByStudentMail(String studentMail);

}
