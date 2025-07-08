package com.pard.gz.zigu.post.entity;

import com.pard.gz.zigu.Image.entity.Image;
import com.pard.gz.zigu.apply.entity.Apply;
import com.pard.gz.zigu.borrowed.entity.Borrowed;
import com.pard.gz.zigu.post.entity.enums.Category;
import com.pard.gz.zigu.post.entity.enums.IsBorrowable;
import com.pard.gz.zigu.school.entity.School;
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
public class Post {
    // post_id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 게시글 작성자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User writer;

    // 게시글의 물품 대여 가능 여부
    @Column(name = "isBorrowable")
    @Enumerated(EnumType.STRING)
    private IsBorrowable isBorrowable;

    public void updateImpossible(){
        this.isBorrowable = IsBorrowable.IMPOSSIBLE;
    }

    public void updatePossible(){
        this.isBorrowable = IsBorrowable.POSSIBLE;
    }

    // 물품 이름
    @Column(name = "item_name", nullable = false)
    private String itemName;

    // 해당 물품의 이미지
    // 🔽 Post 입장에서: 이미지들을 가지고 있음 (1:N)
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images = new ArrayList<>();



    // 카테고리
    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private Category category;

    // 시간 당 가격
    @Column(name = "price_per_hour")
    private Long pricePerHour;

    // 하루 당 가격(일일 가격)
    @Column(name = "price_per_day")
    private Long pricePerDay;

    // 상세 설명
    @Column(name = "description")
    private String description;

    // 어떤 학교의 게시물인지
    @ManyToOne
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    // 한 게시물에 다수의 대여 이력
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Borrowed> borrowedList = new ArrayList<>();

    // 해당 게시물 물품의 대여를 원하는 신청서들
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Apply> applyList = new ArrayList<>();

}
