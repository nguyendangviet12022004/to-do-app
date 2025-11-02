package com.viet.to_do_api.config.security;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import com.viet.to_do_api.entity.auth.Account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountOidcUser implements OidcUser {

    private Account account;
    private Map<String, Object> attributes;
    private OidcIdToken idToken;
    private OidcUserInfo userInfo;

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.account.getAuthorities().stream().map(auth -> new SimpleGrantedAuthority(auth.getName().toString()))
                .toList();
    }

    @Override
    public String getName() {
        return this.account.getEmail();
    }

    @Override
    public Map<String, Object> getClaims() {
        return idToken != null ? idToken.getClaims() : attributes;
    }

    @Override
    public OidcIdToken getIdToken() {
        return this.idToken;
    }

    @Override
    public OidcUserInfo getUserInfo() {
        return this.userInfo;
    }

}
