package com.viet.to_do_api.service.impl;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.viet.to_do_api.constant.AuthorityName;
import com.viet.to_do_api.dto.auth.RegisterRequest;
import com.viet.to_do_api.entity.Account;
import com.viet.to_do_api.entity.Authority;
import com.viet.to_do_api.exception.auth.EmailExistsException;
import com.viet.to_do_api.mapper.AccountMapper;
import com.viet.to_do_api.repository.AccountRepository;
import com.viet.to_do_api.repository.AuthorityRepository;
import com.viet.to_do_api.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AccountRepository accountRepository;
    private final AuthorityRepository authorityRepository;
    private final AccountMapper accountMapper;
    private final PasswordEncoder passwordEncoder;

    private Authority retiriveAuthority(AuthorityName name) {

        // get authoriy from db or else save new
        return authorityRepository.findByName(name)
                .orElse(authorityRepository.save(Authority.builder().name(name).build()));
    }

    @Override
    public void register(RegisterRequest request) {

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

    }

    @Override
    public boolean checkExistEmail(String email) {
        return this.accountRepository.existsByEmail(email);
    }

}
