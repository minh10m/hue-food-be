package com.minh.Online.Food.Ordering.adapters.security;

import com.minh.Online.Food.Ordering.domain.ports.out.UserRepositoryPort;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomerUserDetailsService implements UserDetailsService {

    private final UserRepositoryPort users;

    public CustomerUserDetailsService(UserRepositoryPort users) { this.users = users; }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var u = users.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return org.springframework.security.core.userdetails.User
                .withUsername(u.email())
                .password(u.passwordHash())
                .roles(u.role().name())
                .disabled(!u.enabled())
                .build();
    }
}
