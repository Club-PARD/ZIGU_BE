package com.pard.gz.zigu.school.repository;

import com.pard.gz.zigu.post.entity.Post;
import com.pard.gz.zigu.school.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SchoolRepo extends JpaRepository<School, Long> {
    Optional<School> findBySchoolName(String schoolName);
//    List<Post>
}
