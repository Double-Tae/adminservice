package com.tae.adminservice.repository.post;


import com.tae.adminservice.domain.member.Member;
import com.tae.adminservice.domain.post.Post;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {

    List<Post> findAllBySeller_MemberId(String memberId);




}
