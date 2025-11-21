package com.viet.to_do_api.config;

import java.util.Optional;

import com.viet.to_do_api.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.viet.to_do_api.config.security.AccountUserDetails;
import com.viet.to_do_api.entity.auth.Account;

public class SpringSecurityAuditorAware implements AuditorAware<Account> {

    @Override
    @NonNull
    public Optional<Account> getCurrentAuditor() {
        var opt = Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .map(AccountUserDetails.class::cast)
                .map(AccountUserDetails::getAccount);

        if (opt.isPresent()) {
            return opt;
        } else {
            return Optional.empty();
        }
    }

}
