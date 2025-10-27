package com.viet.to_do_api.config.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.viet.to_do_api.entity.Account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountUserDetails implements UserDetails {

    private Account account;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return account.getAuthorities().stream()
                .map(auth -> new SimpleGrantedAuthority(auth.getName().toString()))
                .toList();
    }

    @Override
    public String getPassword() {
        return account.getPassword();
    }

    @Override
    public String getUsername() {
        return account.getEmail();
    }

    @Override
    public boolean isEnabled() {
        return account.isActive();
    }
}
