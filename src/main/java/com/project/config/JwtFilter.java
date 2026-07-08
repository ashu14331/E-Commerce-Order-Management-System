package com.project.config;

import com.project.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;


@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        String path = request.getServletPath();
        if (path.startsWith("/swagger-ui") || path.startsWith("/v3/api-docs")){
            filterChain.doFilter(request, response);
            return;
        }

        // Token Validation
        if (header != null && header.startsWith("Bearer ")) {

            // Extract token & username
            String token = header.substring(7);
            String username = jwtUtil.extractUsername(token);

            // Extract roles from token
            Claims claims = jwtUtil.extractAllClaims(token);
            List<String> roles = claims.get("roles", List.class);

            // Convert roles to authorities
            List<SimpleGrantedAuthority> authorities =
                    roles.stream()
                            .map(SimpleGrantedAuthority::new)
                            .toList();

            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            authorities
                    );

            // Set Authentication in SecurityContext
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        // Continue request
        filterChain.doFilter(request, response);
    }
    }

