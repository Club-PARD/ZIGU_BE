package com.pard.gz.zigu.borrowed.entity;

import com.pard.gz.zigu.borrowed.entity.enums.BorrowStatus;
import com.pard.gz.zigu.post.entity.Post;
import com.pard.gz.zigu.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Borrowed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "borrowed_id")
    private User borrower;

    @Column(name = "borrowStatus")
    @Enumerated(EnumType.STRING)
    private BorrowStatus borrowStatus;

}
