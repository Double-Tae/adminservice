package com.tae.adminservice.domain.post;


import com.tae.adminservice.domain.member.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterestedPost {

    @Id
    private Long Id;

    @ManyToOne
    private Member interestedMember;

    @ManyToOne
    private Post post;
}
