package com.example.usermanagement.persistence.entity;

import com.example.usermanagement.persistence.value.Interest;
import com.example.usermanagement.persistence.value.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user")
public class User {

    // 회원 식별 번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    /* 사용자의 관심분야 목록 */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_interest")
    @Column(name = "interest")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Set<Interest> interests = new HashSet<>();

    /* 회원이 가진 권한 목록 (Spring Security) */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role")
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private List<Role> roles = new ArrayList<>();

}