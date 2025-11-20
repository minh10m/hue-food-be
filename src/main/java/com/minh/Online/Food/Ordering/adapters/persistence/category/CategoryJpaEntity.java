package com.minh.Online.Food.Ordering.adapters.persistence.category;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name = "categories")
public class CategoryJpaEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="restaurant_id", nullable = false)
    private Long restaurantId;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false)
    private boolean active;
}

