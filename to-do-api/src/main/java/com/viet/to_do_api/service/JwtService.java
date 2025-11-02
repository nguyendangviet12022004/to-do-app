package com.viet.to_do_api.service;

import java.util.Collection;
import java.util.function.Function;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import io.jsonwebtoken.Claims;

public interface JwtService {
    public String generateToken(String subject, long expirationTimeInMillis, String secretKey, String issuer,
            Claims claims);

    public String genereateAccessToken(String username, Collection<? extends GrantedAuthority> authorities);

    public String genereateRefreshToken(String username, Collection<? extends GrantedAuthority> authorities);

    public String genereateAccessToken(Authentication authentication);

    public String genereateRefreshToken(Authentication authentication);

    public Claims extractAllClaims(String token, String secretKey);

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver, String secretKey);

    public <T> T extractAccessTokenClaim(String token, Function<Claims, T> claimsResolver);

    public <T> T extractRefreshTokenClaim(String token, Function<Claims, T> claimsResolver);

    public String refreshToken(String refreshToken);

    public Authentication extractAuthentication(String token);
}
