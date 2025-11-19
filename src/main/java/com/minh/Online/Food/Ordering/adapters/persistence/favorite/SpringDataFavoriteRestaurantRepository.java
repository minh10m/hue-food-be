package com.minh.Online.Food.Ordering.adapters.persistence.favorite;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataFavoriteRestaurantRepository
        extends JpaRepository<FavoriteRestaurantJpaEntity, Long> {

    boolean existsByUserIdAndRestaurantId(Long userId, Long restaurantId);

    void deleteByUserIdAndRestaurantId(Long userId, Long restaurantId);

    List<FavoriteRestaurantJpaEntity> findByUserId(Long userId);
}

