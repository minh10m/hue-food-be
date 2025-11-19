package com.minh.Online.Food.Ordering.domain.ports.out;


import com.minh.Online.Food.Ordering.domain.model.FavoriteRestaurant;

import java.util.List;

public interface FavoriteRestaurantRepositoryPort {

    boolean existsByUserIdAndRestaurantId(Long userId, Long restaurantId);

    FavoriteRestaurant save(FavoriteRestaurant favorite);

    void deleteByUserIdAndRestaurantId(Long userId, Long restaurantId);

    List<FavoriteRestaurant> findByUserId(Long userId);
}


