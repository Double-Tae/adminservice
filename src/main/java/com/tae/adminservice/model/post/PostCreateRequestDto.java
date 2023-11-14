package com.tae.adminservice.model.post;


import com.tae.adminservice.domain.member.Member;
import com.tae.adminservice.domain.post.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostCreateRequestDto {
    String title;
    String content;
    Long price;
    String imagePath;
    String memberId;

    public Post toEntity(Member member){
        return Post.builder()
                .title(title)
                .content(content)
                .interest(0)
                .view(0)
                .price(price)
                .seller(member)
                .writeTime(LocalDateTime.now())
                .build();
    }
}
