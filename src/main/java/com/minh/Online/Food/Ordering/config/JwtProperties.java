package com.minh.Online.Food.Ordering.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

public class JwtProperties {
    @Value("${application.security.jwt.secret-key}")
    private String secret;
    @Value("${application.security.jwt.access-token-expiration}")
    private long accessTtlSeconds = 900;   // 15m
    @Value("${application.security.jwt.refresh-token-expiration}")
    private long refreshTtlSeconds = 1209600; // 14d

    public String getSecret() { return secret; }
    public void setSecret(String secret) { this.secret = secret; }
    public long getAccessTtlSeconds() { return accessTtlSeconds; }
    public void setAccessTtlSeconds(long v) { this.accessTtlSeconds = v; }
    public long getRefreshTtlSeconds() { return refreshTtlSeconds; }
    public void setRefreshTtlSeconds(long v) { this.refreshTtlSeconds = v; }
}
