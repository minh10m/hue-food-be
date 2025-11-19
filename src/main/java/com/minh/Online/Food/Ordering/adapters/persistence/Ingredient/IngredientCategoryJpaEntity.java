package com.minh.Online.Food.Ordering.adapters.persistence.Ingredient;

import com.minh.Online.Food.Ordering.adapters.persistence.restaurant.RestaurantJpaEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name = "ingredient_categories")
public class IngredientCategoryJpaEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "restaurant_id", nullable = false)
    private RestaurantJpaEntity restaurant;

    @Column(nullable=false) private String name;
    @Column(length=1000) private String description;
    @Column(nullable=false) private boolean active;
}

