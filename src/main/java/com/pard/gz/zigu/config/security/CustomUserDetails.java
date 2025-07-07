package com.pard.gz.zigu.config.security;

import com.pard.gz.zigu.user.entity.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
public class CustomUserDetails implements UserDetails {

    private final User user;


    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public String getUsername() {
        return user.getStudentMail();  // 로그인 ID
    }

    @Override
    public String getPassword() {
        return user.getPassword();     // 비번
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null; // 권한 필요 없으면 null
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정 만료 X
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 계정 잠김 X
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 비번 만료 X
    }

    @Override
    public boolean isEnabled() {
        return true; // 활성화 여부
    }
}

