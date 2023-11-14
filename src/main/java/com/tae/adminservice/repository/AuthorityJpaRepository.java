package com.tae.adminservice.repository;

import com.tae.adminservice.domain.member.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityJpaRepository extends JpaRepository<Authority,String> {
    Authority findByAuthorityName(String authority);
}
