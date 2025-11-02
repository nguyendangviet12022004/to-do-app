package com.viet.to_do_api.service.impl;

import java.security.Principal;
import java.util.Collection;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import com.viet.to_do_api.config.security.AccountOidcUser;
import com.viet.to_do_api.config.security.AccountUserDetails;
import com.viet.to_do_api.entity.auth.Account;
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

        String strAuthorities = authorities.stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        return generateToken(username, accessTokenExpiration, accessTokenSecret, issuer,
                Jwts.claims().add("authorities", strAuthorities).build());
    }

    @Override
    public String genereateRefreshToken(String username, Collection<? extends GrantedAuthority> authorities) {
        String strAuthorities = authorities.stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        return generateToken(username, accessTokenExpiration, refreshTokenSecret, issuer,
                Jwts.claims().add("authorities", strAuthorities).build());
    }

    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver, String secretKey) {

        Claims claims = extractAllClaims(token, secretKey);
        return claimsResolver.apply(claims);

    }

    @Override
    public <T> T extractAccessTokenClaim(String token, Function<Claims, T> claimsResolver) {
        return extractClaim(token, claimsResolver, accessTokenSecret);
    }

    @Override
    public <T> T extractRefreshTokenClaim(String token, Function<Claims, T> claimsResolver) {
        return extractClaim(token, claimsResolver, refreshTokenSecret);
    }

    @Override
    public Claims extractAllClaims(String token, String secretKey) {
        return (Claims) Jwts.parser().verifyWith(getSignKey(secretKey)).build().parse(token).getPayload();
    }

    @Override
    public Authentication extractAuthentication(String token) {
        String username = extractAccessTokenClaim(token, Claims::getSubject);

        String strAuthorities = extractAccessTokenClaim(token, (claims -> claims.get("authorities", String.class)));

        Integer id = extractAccessTokenClaim(token, (claims -> claims.get("id", Integer.class)));

        var authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(strAuthorities);
        AccountUserDetails userDetails = AccountUserDetails.builder()
                .account(Account.builder()
                        .id(id)
                        .email(username)
                        .build())
                .build();
        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }

    @Override
    public String refreshToken(String refreshToken) {
        String username = extractRefreshTokenClaim(refreshToken, Claims::getSubject);

        String strAuthorities = extractRefreshTokenClaim(refreshToken,
                (claims -> claims.get("authorities", String.class)));

        var authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(strAuthorities);

        return genereateAccessToken(username, authorities);
    }

    @Override
    public String genereateAccessToken(Authentication authentication) {
        var principal = authentication.getPrincipal();

        int id = 0;

        if (principal instanceof AccountUserDetails) {
            id = ((AccountUserDetails) principal).getAccount().getId();
        } else if (principal instanceof AccountOidcUser) {
            id = ((AccountOidcUser) principal).getAccount().getId();
        }
        String strAuthorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        return generateToken(authentication.getName(), accessTokenExpiration, accessTokenSecret, issuer,
                Jwts.claims().add("authorities", strAuthorities).add("id", id).build());
    }

    @Override
    public String genereateRefreshToken(Authentication authentication) {
        var principal = authentication.getPrincipal();

        int id = 0;

        if (principal instanceof AccountUserDetails) {
            id = ((AccountUserDetails) principal).getAccount().getId();
        } else if (principal instanceof AccountOidcUser) {
            id = ((AccountOidcUser) principal).getAccount().getId();
        }
        String strAuthorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        return generateToken(authentication.getName(), refreshTokenExpiration, accessTokenSecret, issuer,
                Jwts.claims().add("authorities", strAuthorities).add("id", id).build());
    }
}
