package com.pard.gz.zigu.borrowed.repository;

import com.pard.gz.zigu.borrowed.entity.Borrowed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowedRepo extends JpaRepository<Borrowed, Long> {
}
