package com.viet.to_do_api.service.impl;

import java.util.Collection;
import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.viet.to_do_api.service.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${application.jwt.access-token.secret}")
    private String accessTokenSecret;

    @Value("${application.jwt.refresh-token.secret}")
    private String refreshTokenSecret;

    @Value("${application.jwt.access-token.expiration}")
    private long accessTokenExpiration;

    @Value("${application.jwt.refresh-token.expiration}")
    private long refreshTokenExpiration;

    @Value("${application.jwt.issuer}")
    private String issuer;

    private SecretKey getSignKey(String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public String generateToken(String subject, long expirationTimeInMillis, String secretKey, String issuer,
            Claims claims) {
        return Jwts.builder()
                .subject(subject)
                .issuer(issuer)
                .claims(claims)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTimeInMillis))
                .signWith(getSignKey(secretKey))
                .compact();
    }

    @Override
    public String genereateAccessToken(String username,
            Collection<? extends GrantedAuthority> authorities) {
        return generateToken(username, accessTokenExpiration, accessTokenSecret, issuer,
                Jwts.claims().add("authorities", authorities).build());
    }

    @Override
    public String genereateRefreshToken(String username, Collection<? extends GrantedAuthority> authorities) {
        return generateToken(username, refreshTokenExpiration, refreshTokenSecret, issuer,
                Jwts.claims().add("authorities", authorities).build());
    }

    @Override
    public boolean validateToken(String token, String secretKey) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validateToken'");
    }

    @Override
    public boolean validateAccessToken(String token) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validateAccessToken'");
    }

    @Override
    public boolean validateRefreshToken(String token) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validateRefreshToken'");
    }

    @Override
    public <T> T getClaim(String token, Function<String, T> claimsResolver, String secretKey) {

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getClaim'");
    }

    @Override
    public <T> T getAccessTokenClaim(String token, Function<String, T> claimsResolver) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAccessTokenClaim'");
    }

    @Override
    public <T> T getRefreshTokenClaim(String token, Function<String, T> claimsResolver) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRefreshTokenClaim'");
    }

    @Override
    public Claims getAllClaims(String token, String secretKey) {
        return (Claims) Jwts.parser().verifyWith(getSignKey(secretKey)).build().parse(token).getPayload();
    }

}
