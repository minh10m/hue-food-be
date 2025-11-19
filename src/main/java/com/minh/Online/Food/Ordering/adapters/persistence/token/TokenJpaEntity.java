package com.minh.Online.Food.Ordering.adapters.persistence.token;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name = "tokens")
public class TokenJpaEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private Long userId;

    @Column(nullable=false, unique=true, length=2048)
    private String token;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false, length=16)
    private TokenType type;

    @Column(nullable=false)
    private boolean revoked;

    @Column(nullable=false)
    private Instant expiresAt;

    public enum TokenType { ACCESS, REFRESH }
}
