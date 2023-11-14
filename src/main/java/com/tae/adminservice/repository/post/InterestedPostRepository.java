package com.tae.adminservice.repository.post;

import com.tae.adminservice.domain.member.Member;
import com.tae.adminservice.domain.post.InterestedPost;
import com.tae.adminservice.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InterestedPostRepository extends JpaRepository<InterestedPost,Long> {
    List<InterestedPost> findAllByInterestedMember_MemberId(String memberId);


    @Query(value = "SELECT * FROM InterestedPost ip "+
                    "WHERE ip.interestedMember.memberId LIKE :memberId " +
                    "AND ip.post.id = :postId")
    InterestedPost findByMemberAndPostId(@Param(value = "memberId") String memberId,
                                @Param(value = "postId") Long postId);
}
