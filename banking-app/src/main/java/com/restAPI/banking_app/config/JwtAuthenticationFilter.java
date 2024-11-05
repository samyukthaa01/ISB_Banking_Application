package com.restAPI.banking_app.config;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@Component

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtDecoder jwtDecoder;

    public JwtAuthenticationFilter(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Extract and validate the token
        String token = extractToken(request);
        if (token != null) {
            // Decode the token
            Jwt jwt = jwtDecoder.decode(token);

            // Extract authorities from JWT claims (example implementation)
            Collection<? extends GrantedAuthority> authorities = extractAuthorities(jwt.getClaims());

            // Create JwtAuthenticationToken with authorities
            JwtAuthenticationToken authentication = new JwtAuthenticationToken(jwt, authorities);

            // Set the authentication in the SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private Collection<? extends GrantedAuthority> extractAuthorities(Map<String, ?> claims) {
        Object roles = claims.get("roles");
        if (roles instanceof Collection<?>) {
            return ((Collection<?>) roles).stream()
                    .map(role -> {
                        if (role instanceof Map) {
                            return (String) ((Map<?, ?>) role).get("name"); // Adjust based on your JWT structure
                        } else if (role instanceof String) {
                            return (String) role;
                        }
                        return null;
                    })
                    .filter(roleName -> roleName != null) // Filter out nulls
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

}

