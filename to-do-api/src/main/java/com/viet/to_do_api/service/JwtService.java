package com.viet.to_do_api.service;

import java.util.Collection;
import java.util.function.Function;

import org.springframework.security.core.GrantedAuthority;

import io.jsonwebtoken.Claims;

public interface JwtService {
    public String generateToken(String subject, long expirationTimeInMillis, String secretKey, String issuer,
            Claims claims);

    public String genereateAccessToken(String username, Collection<? extends GrantedAuthority> authorities);

    public String genereateRefreshToken(String username, Collection<? extends GrantedAuthority> authorities);

    public boolean validateToken(String token, String secretKey);

    public boolean validateAccessToken(String token);

    public boolean validateRefreshToken(String token);

    public Claims getAllClaims(String token, String secretKey);

    public <T> T getClaim(String token, Function<String, T> claimsResolver, String secretKey);

    public <T> T getAccessTokenClaim(String token, Function<String, T> claimsResolver);

    public <T> T getRefreshTokenClaim(String token, Function<String, T> claimsResolver);
}
