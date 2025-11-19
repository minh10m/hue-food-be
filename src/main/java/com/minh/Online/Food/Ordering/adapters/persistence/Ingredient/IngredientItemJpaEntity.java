package com.minh.Online.Food.Ordering.adapters.persistence.Ingredient;

import com.minh.Online.Food.Ordering.adapters.persistence.restaurant.RestaurantJpaEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name = "ingredient_items")
public class IngredientItemJpaEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "restaurant_id", nullable = false)
    private RestaurantJpaEntity restaurant;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "category_id", nullable = false)
    private IngredientCategoryJpaEntity category;

    @Column(nullable=false) private String name;
    private String unit;
    @Column(precision = 18, scale = 2) private BigDecimal price;
    @Column(nullable=false) private boolean inStock;
    @Column(nullable=false) private boolean active;
}
