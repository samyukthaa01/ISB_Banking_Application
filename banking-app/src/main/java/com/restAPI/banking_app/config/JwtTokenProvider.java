package com.restAPI.banking_app.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component

public class JwtTokenProvider {

    private SecretKey secretKey;

    public JwtTokenProvider() {
        // Automatically generate a secure key for HMAC-SHA256
        secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    @Value("${app.jwt-expiration}")
    private long jwtExpirationDate;

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expiryDate = new Date(currentDate.getTime() + jwtExpirationDate);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(currentDate)
//                .setExpiration(expiryDate)
                .setExpiration(expiryDate)
                .signWith(secretKey)
                .compact();
    }


    public String getUsername(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parse(token);
            return true;

        } catch (ExpiredJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }


}
