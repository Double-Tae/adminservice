package com.tae.adminservice.domain.member;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(length = 30, name = "memberId")
    private String memberId;

    @Column(length = 10, name = "name")
    private String name;

    @Column(length = 13, name = "phoneNum")
    private String phoneNum;

    @Column(name = "password", length = 100)
    private String password;

    @Column(name = "activated")
    private boolean activated;

}
