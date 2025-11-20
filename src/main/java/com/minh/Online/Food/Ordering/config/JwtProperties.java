package com.minh.Online.Food.Ordering.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
public class JwtProperties {
    @Value("${application.security.jwt.secret-key}")
    private String secret;
    @Value("${application.security.jwt.access-token-expiration}")
    private long accessTtlSeconds = 900;   // 15m
    @Value("${application.security.jwt.refresh-token-expiration}")
    private long refreshTtlSeconds = 1209600; // 14d

}
