package com.viet.to_do_api.config.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;

import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import com.viet.to_do_api.service.JwtService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Oauth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        AccountOidcUser oidcUser = (AccountOidcUser) authentication.getPrincipal();

        String accessToken = jwtService.genereateAccessToken(oidcUser.getEmail(), oidcUser.getAuthorities());
        String refreshToken = jwtService.genereateRefreshToken(oidcUser.getEmail(), oidcUser.getAuthorities());

        response.sendRedirect(String.format("http://localhost:4200/auth/oauth2/callback?accessToken=%s&refreshToken=%s",
                accessToken, refreshToken));

    }

}
