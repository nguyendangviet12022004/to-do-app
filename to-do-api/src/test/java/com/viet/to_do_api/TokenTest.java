package com.viet.to_do_api;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.viet.to_do_api.constant.TokenCodeType;
import com.viet.to_do_api.entity.Token;
import com.viet.to_do_api.repository.TokenRepository;
import com.viet.to_do_api.service.impl.TokenServiceImpl;

@ExtendWith(MockitoExtension.class)
public class TokenTest {

    @Mock
    private TokenRepository tokenRepository;

    @InjectMocks
    private TokenServiceImpl tokenService;

    @BeforeEach
    void init() {
        given(this.tokenRepository.save(any(Token.class)))
                .willAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    void testGenerateToken_WhenValidArgument_ShouldReturnToken() {
        var token = this.tokenService.generateToken(1, 60, TokenCodeType.ACTIVATE_ACCOUNT);

        Assert.notNull(token, "token must be not null");
    }

    @AfterEach
    void tearDown() {
    }

}
