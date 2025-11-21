package com.viet.to_do_api.config.security;

import java.io.IOException;

import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;

import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import com.viet.to_do_api.service.JwtService;

import ch.qos.logback.core.util.StringUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Oauth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final Environment environment;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

//        AccountOidcUser oidcUser = (AccountOidcUser) authentication.getPrincipal();

        String accessToken = jwtService.genereateAccessToken(authentication);
        String refreshToken = jwtService.genereateRefreshToken(authentication);

        String uiUrl = environment.getProperty("ui.url");
        if (StringUtil.isNullOrEmpty(uiUrl))
            uiUrl = "http://localhost:4200";
        response.sendRedirect(String.format("%s/auth/oauth2/callback?accessToken=%s&refreshToken=%s", uiUrl,
                accessToken, refreshToken));

    }

}
