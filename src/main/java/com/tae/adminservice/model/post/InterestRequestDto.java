package com.tae.adminservice.model.post;

import com.tae.adminservice.domain.post.InterestedPost;
import lombok.Getter;


@Getter
public class InterestRequestDto {
    Long postId;
    String memberId;
}
