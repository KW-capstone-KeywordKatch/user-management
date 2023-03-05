package com.example.usermanagement.security;

import com.example.usermanagement.persistence.entity.User;
import com.example.usermanagement.persistence.value.Role;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final User user;

    /**
     * 해당 회원의 접근 권한 반환
     * @return List<SimpleGrantedAuthority>
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.user.getRoles()
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    /* Spring Security에서 username은 계정을 식별할 수 있는 고유한 값을 의미한다.
     * (이메일, PK 등 가능)
     * 회원의 이름만을 뜻하는게 아니다.*/
    @Override
    public String getUsername() {
        return user.getUserId().toString();
    }

    // 해당 기능 없음
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 해당 기능 없음
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 비밀번호 만료 여부 기능 없음
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 사용자 활성화 여부 기능 없음
    @Override
    public boolean isEnabled() {
        return true;
    }
}
