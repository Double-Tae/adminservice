package com.tae.adminservice.repository.post;

import com.tae.adminservice.domain.member.Member;
import com.tae.adminservice.domain.post.PreviousPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PreviousPostRepository extends JpaRepository<PreviousPost,Long> {
    List<PreviousPost> findAllByBuyer_MemberId(String memberId);
}
