package com.minh.Online.Food.Ordering.adapters.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class AuthUtils {

    public Long currentUserId(Authentication auth) {
        if (auth == null || !auth.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
        }

        Object principal = auth.getPrincipal();

        if (principal instanceof AuthPrincipal ap) {
            return ap.getUserId();
        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid authentication principal");
    }

    public String currentEmail(Authentication auth) {
        if (auth == null || !auth.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
        }

        Object principal = auth.getPrincipal();

        if (principal instanceof AuthPrincipal ap) {
            return ap.getEmail();
        }

        return auth.getName();
    }

    public String currentRole(Authentication auth) {
        if (auth == null || !auth.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
        }

        Object principal = auth.getPrincipal();

        if (principal instanceof AuthPrincipal ap) {
            return ap.getRole();
        }

        return null;
    }
}

