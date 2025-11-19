package com.minh.Online.Food.Ordering.domain.ports.out;


import com.minh.Online.Food.Ordering.domain.model.Food;

import java.util.List;
import java.util.Optional;

public interface FoodRepositoryPort {
    Food save(Food f);
    Optional<Food> findById(Long id);
    Optional<Food> findByIdAndOwnerId(Long id, Long ownerId);
    List<Food> findPublic(Long restaurantId, Long categoryId, int page, int size);
    List<Food> findByOwner(Long ownerId);
    List<Food> findByOwnerAndRestaurant(Long ownerId, Long restaurantId);
    void deleteById(Long id);
}

