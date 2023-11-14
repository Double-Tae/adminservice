package com.tae.adminservice.domain.post;


import com.tae.adminservice.domain.member.Member;
import jakarta.persistence.*;

@Entity
@Table
public class PreviousPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Post post;
    @ManyToOne
    private Member buyer;
}
