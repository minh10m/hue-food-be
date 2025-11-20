package com.minh.Online.Food.Ordering.adapters.security;

import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtServiceAdapter jwtService;
    private static final AntPathMatcher ANT = new AntPathMatcher();

    public JwtAuthenticationFilter(JwtServiceAdapter jwtService) {
        this.jwtService = jwtService;
    }

    private boolean isPublicPath(String path) {
        return ANT.match("/v3/api-docs/**", path)
                || ANT.match("/swagger-ui/**", path)
                || ANT.match("/swagger-ui.html", path)
                || ANT.match("/swagger-resources/**", path)
                || ANT.match("/configuration/**", path)
                || ANT.match("/webjars/**", path)
                || ANT.match("/api/auth/**", path)
                || ANT.match("/error", path)
                || ANT.match("/actuator/**", path)
                || ANT.match("/public/**", path);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return "OPTIONS".equalsIgnoreCase(request.getMethod())
                || isPublicPath(request.getServletPath());
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            String authHeader = request.getHeader("Authorization");

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);

                try {
                    if (jwtService.isValid(token)) {
                        String email = jwtService.extractEmail(token);
                        Long userId = jwtService.extractUserId(token);
                        String role = jwtService.extractRole(token);

                        if (email != null && userId != null) {
                            AuthPrincipal principal = new AuthPrincipal(userId, email, role);

                            var authorities = (role != null)
                                    ? List.of(new SimpleGrantedAuthority("ROLE_" + role))
                                    : List.<SimpleGrantedAuthority>of();

                            UsernamePasswordAuthenticationToken authToken =
                                    new UsernamePasswordAuthenticationToken(
                                            principal, null, authorities);

                            authToken.setDetails(
                                    new WebAuthenticationDetailsSource().buildDetails(request));

                            SecurityContextHolder.getContext().setAuthentication(authToken);
                        }
                    }
                } catch (Exception ignored) {
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
