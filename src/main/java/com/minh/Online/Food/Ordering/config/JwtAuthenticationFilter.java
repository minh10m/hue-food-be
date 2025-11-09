package com.minh.Online.Food.Ordering.config;

import com.minh.Online.Food.Ordering.modules.token.JwtService;
import com.minh.Online.Food.Ordering.modules.user.service.CustomerUserDetailsService;
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomerUserDetailsService userDetailsService;

    private static final AntPathMatcher ANT = new AntPathMatcher();

    public JwtAuthenticationFilter(JwtService jwtService, CustomerUserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    /** Public (no-auth) endpoints.
     *  NOTE: do NOT put /api/restaurants/** here. Only GET is public (handled in SecurityConfig).
     */
    private boolean isPublicPath(String path) {
        return ANT.match("/v3/api-docs/**", path)
                || ANT.match("/swagger-ui/**", path)
                || ANT.match("/swagger-ui.html", path)
                || ANT.match("/swagger-resources/**", path)
                || ANT.match("/configuration/**", path)
                || ANT.match("/webjars/**", path)
                || ANT.match("/login/**", path)
                || ANT.match("/register/**", path)
                || ANT.match("/refresh_token/**", path)
                || ANT.match("/forgot-password/**", path)
                || ANT.match("/error", path)
                || ANT.match("/actuator/**", path)
                || ANT.match("/public/**", path);
    }

    /** Skip filtering for public paths and CORS preflight. */
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

        // Only handle if no auth yet and we have a Bearer token
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                String email = null;
                try {
                    email = jwtService.extractEmail(token);
                } catch (Exception ignored) {
                    // optionally log invalid token format/claims
                }

                if (email != null) {
                    try {
                        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                        if (jwtService.isValid(token, userDetails)) {
                            UsernamePasswordAuthenticationToken authToken =
                                    new UsernamePasswordAuthenticationToken(
                                            userDetails, null, userDetails.getAuthorities());
                            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(authToken);
                        }
                    } catch (Exception ignored) {
                        // optionally log loadUserByUsername errors
                    }
                }
            }
        }

        filterChain.doFilter(request, response);
        // Do not clear the context here.
    }
}
