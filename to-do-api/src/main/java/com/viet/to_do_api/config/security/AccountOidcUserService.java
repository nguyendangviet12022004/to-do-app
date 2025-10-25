package com.viet.to_do_api.config.security;

import java.util.List;
import java.util.Map;

import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import com.viet.to_do_api.constant.AuthorityName;
import com.viet.to_do_api.entity.Account;
import com.viet.to_do_api.entity.Authority;
import com.viet.to_do_api.repository.AccountRepository;
import com.viet.to_do_api.repository.AuthorityRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountOidcUserService extends OidcUserService {

    private final AccountRepository accountRepository;
    private final AuthorityRepository authorityRepository;

    private Authority retiriveAuthority(AuthorityName name) {

        // get authoriy from db or else save new
        return authorityRepository.findByName(name)
                .orElse(authorityRepository.save(Authority.builder().name(name).build()));
    }

    private AccountOidcUser registerOidcUser(OidcUser user) {
        Map<String, Object> attribute = user.getAttributes();
        String email = user.getEmail();
        Account account = this.accountRepository.findByEmail(email).orElse(new Account());
        OidcUserInfo userInfo = user.getUserInfo();
        OidcIdToken idToken = user.getIdToken();

        // if email is not exist
        if (!accountRepository.existsByEmail(email)) {
            account.setEmail(email);
            account.setActive(true);
            account.setAuthorities(List.of(retiriveAuthority(AuthorityName.ROLE_USER)));
            account = this.accountRepository.save(account);

        }

        // return oidc
        return AccountOidcUser.builder()
                .account(account)
                .idToken(idToken)
                .userInfo(userInfo)
                .attributes(attribute)
                .build();
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {

        // get all info
        OidcUser user = super.loadUser(userRequest);
        return registerOidcUser(user);
    }

}
