package com.minh.Online.Food.Ordering.adapters.persistence.favorite;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "favorite_restaurants",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "restaurant_id"})
)
public class FavoriteRestaurantJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "restaurant_id", nullable = false)
    private Long restaurantId;
}

