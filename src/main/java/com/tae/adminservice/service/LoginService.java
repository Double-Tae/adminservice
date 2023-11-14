package com.tae.adminservice.service;

import com.tae.adminservice.domain.member.Member;
import com.tae.adminservice.repository.MemberJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.userdetails.User;
@Slf4j
@Service
public class LoginService implements UserDetailsService {
    private final MemberJpaRepository memberJpaRepository;
    @Autowired
    public LoginService(MemberJpaRepository memberJpaRepository) {
        this.memberJpaRepository = memberJpaRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String memberName) throws UsernameNotFoundException {
        return memberJpaRepository.findOneWithAuthoritiesByMemberId(memberName)
                .map(member -> createMember(memberName,member))
                .orElseThrow(()->new UsernameNotFoundException(memberName + " -> 데이터베이스에서 찾을 수 없습니다."));
    }

    private User createMember(String memberName, Member member){
        if (!member.isActivated()) {
            throw new RuntimeException(memberName + " -> 활성화되어 있지 않습니다.");
        }

        List<GrantedAuthority> grantedAuthorities = member.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority().getAuthorityName()))
                .collect(Collectors.toList());

        return new User(member.getMemberId(),
                member.getPassword(),
                grantedAuthorities);
    }
}
