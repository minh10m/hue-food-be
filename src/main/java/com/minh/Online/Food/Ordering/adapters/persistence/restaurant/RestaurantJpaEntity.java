package com.minh.Online.Food.Ordering.adapters.persistence.restaurant;

import com.minh.Online.Food.Ordering.adapters.persistence.user.UserJpaEntity;
import com.minh.Online.Food.Ordering.domain.model.RestaurantStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name = "restaurants")
public class RestaurantJpaEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "owner_id", nullable = false)
    private UserJpaEntity owner;

    @Column(nullable=false) private String name;
    @Column(length=2000) private String description;
    private String cuisineType;

    // đơn giản hóa địa chỉ
    private String street;
    private String city;

    private String email;
    private String mobile;
    private String twitter;
    private String instagram;

    private String openingHours;
    private String image;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RestaurantStatus status;

    @Column(nullable=false) private Instant createdAt;
    @Column(nullable=false) private Instant updatedAt;

}

