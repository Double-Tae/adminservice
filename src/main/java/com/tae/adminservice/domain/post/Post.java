package com.tae.adminservice.domain.post;


import com.tae.adminservice.domain.member.Member;
import com.tae.adminservice.model.post.PostCreateRequestDto;
import jakarta.persistence.*;
import jakarta.persistence.criteria.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @Column
    private LocalDateTime writeTime;

    @ManyToOne
    private Member seller;

    public void setInterest(int interest){
        this.interest=interest;
    }

    public void setView(int view){
        this.view=view;
    }
}
