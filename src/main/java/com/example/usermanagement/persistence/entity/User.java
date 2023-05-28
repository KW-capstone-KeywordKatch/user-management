package com.example.usermanagement.persistence.entity;

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
@Table(name = "user_info")
public class User {

    // 회원 식별 번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    @Setter(AccessLevel.NONE)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    /* 사용자의 관심분야 목록 */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_interest", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "interest")
    @Builder.Default
    private List<String> interests = new ArrayList<>();

    /* 회원이 가진 권한 목록 (Spring Security) */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role")
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private List<Role> roles = new ArrayList<>();

    /* 0630 0930 1230 1530 1830 중 선택해서 공백으로 연결한 문자열 */
    @Column(name = "email_time")
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private String emailTime;

    /*
    * String 형태를 List<String> 형태로 바꾸어 반환
    * 설정된 시각이 없는 경우 빈 배열 반환 */
    public List<String> getEmailTime() {
        if (emailTime == null)
            return new ArrayList<>();
        else
            return Arrays.asList(emailTime.split(" "));
    }

    /*
    * List<String> 의 원소들을 공백으로 이어 붙여 String 형태로 DB에 저장
    * 빈 배열인 경우 null */
    public void setEmailTime(List<String> desiredTime) {
        if (desiredTime.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (String time: desiredTime)
                sb.append(time).append(" ");
            this.emailTime = sb.toString().trim();
        } else
            emailTime = null;
    }

}