package com.beardedbrothers.sentrybayherald.service;

import com.beardedbrothers.sentrybayherald.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private JwtUtils jwtUtils;

    public Optional<UserDetailsImpl> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetailsImpl) {
                return Optional.of((UserDetailsImpl) principal);
            }
        }
        return Optional.empty();
    }

    public boolean isModeratorOrAdmin() {
        return getCurrentUser()
                .map(userDetails -> userDetails.getAuthorities().stream()
                        .anyMatch(grantedAuthority ->
                                grantedAuthority.getAuthority().equals("ROLE_MODERATOR") ||
                                        grantedAuthority.getAuthority().equals("ROLE_ADMIN")))
                .orElse(false);
    }
}
