package com.pard.gz.zigu.borrowed.repository;

import com.pard.gz.zigu.borrowed.entity.Borrowed;
import com.pard.gz.zigu.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BorrowedRepo extends JpaRepository<Borrowed, Long> {
    List<Borrowed> findAllByBorrower(User borrower);
}
