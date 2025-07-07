package com.pard.gz.zigu.post.repository;

import com.pard.gz.zigu.apply.entity.Apply;
import com.pard.gz.zigu.post.entity.Post;
import com.pard.gz.zigu.school.entity.School;
import com.pard.gz.zigu.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Long> {
    List<Post> findAllByWriter(User user);
    List<Post> findAllBySchool(School school);

}
