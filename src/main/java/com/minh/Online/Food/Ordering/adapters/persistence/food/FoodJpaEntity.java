package com.minh.Online.Food.Ordering.adapters.persistence.food;

import com.minh.Online.Food.Ordering.domain.model.FoodAvailability;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name = "foods")
public class FoodJpaEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="restaurant_id", nullable=false)
    private Long restaurantId;

    @Column(name="category_id")
    private Long categoryId;

    @Column(nullable=false) private String name;
    @Column(length=2000) private String description;

    @Column(nullable=false, precision = 18, scale = 2)
    private BigDecimal price;

    private Boolean vegetarian;
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private FoodAvailability availability;   // <— enum domain, lưu STRING

    @Column(nullable=false) private Instant createdAt;
    @Column(nullable=false) private Instant updatedAt;
}

