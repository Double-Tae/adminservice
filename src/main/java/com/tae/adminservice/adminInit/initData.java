package com.tae.adminservice.adminInit;

import com.tae.adminservice.domain.member.Authority;
import com.tae.adminservice.domain.member.Member;
import com.tae.adminservice.domain.member.MemberAuthority;
import com.tae.adminservice.repository.AuthorityJpaRepository;
import com.tae.adminservice.repository.MemberAuthorityJpaRepository;
import com.tae.adminservice.repository.MemberJpaRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class initData {
        private final PasswordEncoder passwordEncoder;
        private final MemberJpaRepository memberJpaRepository;
        private final MemberAuthorityJpaRepository memberAuthorityJpaRepository;
        private final AuthorityJpaRepository authorityJpaRepository;
        Set<MemberAuthority> set = new HashSet<>();
        @PostConstruct
        public void init(){

            Authority authority1 = Authority.builder()
                    .authorityName("ROLE_USER").build();
            Authority authority2 = Authority.builder()
                    .authorityName("ROLE_ADMIN").build();
            if(authorityJpaRepository.findByAuthorityName(authority1.getAuthorityName())==null){
                authorityJpaRepository.save(authority1);
            }
            if(authorityJpaRepository.findByAuthorityName(authority2.getAuthorityName())==null){
                authorityJpaRepository.save(authority2);
            }
            set.add(MemberAuthority.builder()
                    .authority(authority2)
                    .build());
            Member admin = Member.builder()
                    .memberId("admin")
                    .activated(true)
                    .password(passwordEncoder.encode("12345678"))
                    .build();
            memberJpaRepository.save(admin);
            memberAuthorityJpaRepository.save(MemberAuthority.builder()
                    .member(admin)
                    .authority(authority2)
                    .build());
    }
}
