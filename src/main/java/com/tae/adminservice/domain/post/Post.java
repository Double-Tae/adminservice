package com.tae.adminservice.domain.post;


import com.tae.adminservice.domain.member.Member;
import jakarta.persistence.*;

@Entity
@Table
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private Integer interest;

    @Column
    private Integer view;

    @Column
    private Long price;

    @ManyToOne
    private Member member;

}
