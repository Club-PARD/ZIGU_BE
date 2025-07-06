package com.pard.gz.zigu.Image.entity;

import com.pard.gz.zigu.post.entity.Post;
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
public class Image {

    // image_id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // imade url, *프론트에 넘겨줄 때 동적으로 암호화 후 reponse 해 줄 예정
//    @Column(name = "imageUrl", nullable = false)
//    private String imageUrl;

    // image s3Key(for S3)
    @Column(name = "s3Key", nullable = false)
    private String s3Key;

    // 🔽 Image 입장에서: 어떤 게시물(Post)에 속하는지 가리킴 (N:1)
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    // 생성자 통해 세팅
    public Image(String s3Key, Post post) {
        this.s3Key = s3Key;
        this.post = post;
        // == 연관관계 주인 쪽에서 역방향 컬렉션까지 완성 ==
        post.getImages().add(this);
    }
}
