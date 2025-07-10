package com.pard.gz.zigu.apply.entity;

import com.pard.gz.zigu.apply.entity.enums.ApplyStatus;
import com.pard.gz.zigu.borrowed.entity.enums.UnitOfPeroid;
import com.pard.gz.zigu.post.entity.Post;
import com.pard.gz.zigu.user.entity.User;
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
public class Apply { // 신청서

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "applier")
    private User applier;

    @Column(name = "unitOfPeriod")
    @Enumerated(EnumType.STRING)
    private UnitOfPeroid unitOfPeroid;

    @Column(name = "peroid")
    private Long peroid;

    @Column(name = "totalPrice")
    private Long totalPrice;

    @Column(name = "applyStatus")
    private ApplyStatus applyStatus;
}
