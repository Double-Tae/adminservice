package com.tae.adminservice.model.post;


import com.tae.adminservice.domain.member.Member;
import com.tae.adminservice.domain.post.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostDetailResponseDto {
    String title;
    String content;
    Integer view;
    Integer interest;
    Long price;
    String imagePath;
    String memberId;
    String memberName;
    //List<CommentDetailResponseDto> commentList
}
