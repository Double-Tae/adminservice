package com.tae.adminservice.repository;

import com.tae.adminservice.domain.member.MemberAuthority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberAuthorityJpaRepository extends JpaRepository<MemberAuthority,Long> {
}
