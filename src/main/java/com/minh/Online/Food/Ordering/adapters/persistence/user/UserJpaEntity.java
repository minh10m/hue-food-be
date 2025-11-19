package com.minh.Online.Food.Ordering.adapters.persistence.user;

import com.minh.Online.Food.Ordering.domain.model.UserRole;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name = "users")
public class UserJpaEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable=false)
    private String email;

    @Column(nullable=false)
    private String passwordHash;

    private String fullName;
    private String avatarUrl;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private boolean enabled;
}

