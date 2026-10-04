package com.example.demo.features.jwt;

import com.example.demo.config.AppProperties;
import com.example.demo.features.user.repository.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class JwtService {

    public static final String ACCESS = "access";
    public static final String REFRESH = "refresh";
    private static final String TYPE_CLAIM = "type";

    private final AppProperties appProperties;
    private final SecretKey key;

    public JwtService(AppProperties appProperties) {
        this.appProperties = appProperties;
        this.key = Keys.hmacShaKeyFor(
            appProperties.getJwt().getSecret().getBytes(StandardCharsets.UTF_8)
        );
    }

    public String generateAccessToken(User user) {
        return buildToken(user, ACCESS, appProperties.getJwt().getAccessExpirationMs());
    }

    public String generateRefreshToken(User user) {
        return buildToken(user, REFRESH, appProperties.getJwt().getRefreshExpirationMs());
    }

    public long getAccessExpirationSeconds() {
        return appProperties.getJwt().getAccessExpirationMs() / 1000;
    }

    public Optional<UUID> extractUserId(String token, String expectedType) {
        try {
            Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

            if (!expectedType.equals(claims.get(TYPE_CLAIM, String.class))) {
                return Optional.empty();
            }
            return Optional.of(UUID.fromString(claims.getSubject()));
        } catch (JwtException | IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    private String buildToken(User user, String type, long ttlMs) {
        Date now = new Date();
        return Jwts.builder()
            .subject(user.getId().toString())
            .claim(TYPE_CLAIM, type)
            .issuedAt(now)
            .expiration(new Date(now.getTime() + ttlMs))
            .signWith(key)
            .compact();
    }
}