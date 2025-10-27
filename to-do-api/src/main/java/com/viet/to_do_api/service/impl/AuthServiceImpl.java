package com.viet.to_do_api.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import com.viet.to_do_api.config.security.AccountOidcUser;
import com.viet.to_do_api.constant.AuthorityName;
import com.viet.to_do_api.constant.TokenCodeType;
import com.viet.to_do_api.dto.auth.request.ActivateAccountRequest;
import com.viet.to_do_api.dto.auth.request.LoginRequest;
import com.viet.to_do_api.dto.auth.request.RefreshTokenRequest;
import com.viet.to_do_api.dto.auth.request.RegisterRequest;
import com.viet.to_do_api.dto.auth.response.JwtResponse;
import com.viet.to_do_api.dto.auth.response.TokenResponse;
import com.viet.to_do_api.entity.Account;
import com.viet.to_do_api.entity.Authority;
import com.viet.to_do_api.exception.auth.EmailExistsException;
import com.viet.to_do_api.exception.auth.EmailNotFoundException;
import com.viet.to_do_api.mapper.AccountMapper;
import com.viet.to_do_api.repository.AccountRepository;
import com.viet.to_do_api.repository.AuthorityRepository;
import com.viet.to_do_api.service.AuthService;
import com.viet.to_do_api.service.JwtService;
import com.viet.to_do_api.service.MailService;
import com.viet.to_do_api.service.TokenService;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AccountRepository accountRepository;
    private final AuthorityRepository authorityRepository;
    private final AccountMapper accountMapper;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    private Authority retiriveAuthority(AuthorityName name) {

        // get authoriy from db or else save new
        return authorityRepository.findByName(name)
                .orElse(authorityRepository.save(Authority.builder().name(name).build()));
    }

    @Override
    public void register(RegisterRequest request) throws Exception {

        // check exist account email
        if (accountRepository.existsByEmail(request.getEmail())) {
            throw new EmailExistsException(String.format("The account with email %s is exists", request.getEmail()));
        }

        Account account = accountMapper.toAccount(request);

        // default role for all user is user
        account.setAuthorities(List.of(retiriveAuthority(AuthorityName.ROLE_USER)));

        // hash password
        account.setPassword(passwordEncoder.encode(account.getPassword()));

        // save to the database
        accountRepository.save(account);

        // todo send email to active account

        getActivateAccountCode(account.getEmail());

    }

    @Override
    public boolean checkExistEmail(String email) {
        return this.accountRepository.existsByEmail(email);
    }

    @Override
    public void getActivateAccountCode(String email) throws MessagingException {

        // find account by email
        Account account = this.accountRepository.findByEmail(email)
                .orElseThrow(() -> new EmailNotFoundException(String.format("The email %s not found", email)));

        // create code for activate
        String code = this.tokenService.generateToken(account.getId(), 60, TokenCodeType.ACTIVATE_ACCOUNT);

        mailService.sendActivateCodeMail(email, code);
    }

    @Override
    public void activateAccount(ActivateAccountRequest request) {
        String code = request.code();

        this.tokenService.validateToken(code);

        TokenResponse response = this.tokenService.getTokenByCode(code);

        // find account and update active
        Integer id = response.getAccountId();
        Account account = this.accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Id not found"));
        account.setActive(true);

        this.accountRepository.save(account);
    }

    @Override
    public JwtResponse login(LoginRequest request) {
        Authentication auth = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        if (auth.isAuthenticated()) {
            String accessToken = jwtService.genereateAccessToken(request.email(),
                    auth.getAuthorities());

            String refreshToken = jwtService.genereateRefreshToken(request.email(),
                    auth.getAuthorities());
            return new JwtResponse(accessToken, refreshToken);
        }
        return null;
    }

    @Override
    public JwtResponse refreshToken(RefreshTokenRequest request) {
        String accessToken = jwtService.refreshToken(request.refreshToken());
        return new JwtResponse(accessToken, request.refreshToken());
    }

}
