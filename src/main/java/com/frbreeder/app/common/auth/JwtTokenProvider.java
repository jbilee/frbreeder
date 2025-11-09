package com.frbreeder.app.common.auth;

import com.frbreeder.app.domain.entity.Workspace;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider implements TokenProvider {

    private final int jwtExpirationMs;
    private final SecretKey key;

    public JwtTokenProvider(@Value("${security.jwt.token.secret-key}") final String jwtSecret, @Value("${security.jwt.token.expire-length}") final int jwtExpirationMs) {
        this.jwtExpirationMs = jwtExpirationMs;
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(Workspace workspace) {
        return Jwts.builder()
                .subject(workspace.getId().toString())
                .claim("name", workspace.getName())
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + jwtExpirationMs))
                .signWith(key)
                .compact();
    }

    public Long getWorkspaceIdFromToken(String token) {
        return Long.valueOf(getJwtClaims(token).getSubject());
    }

    private Claims getJwtClaims(String token) {
        try {
            return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
        } catch (SecurityException | MalformedJwtException | UnsupportedJwtException e) {
            throw new IllegalArgumentException("Token is invalid.");
        } catch (ExpiredJwtException e) {
            throw new IllegalArgumentException("Token has expired.");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Token is empty.");
        }
    }

}
