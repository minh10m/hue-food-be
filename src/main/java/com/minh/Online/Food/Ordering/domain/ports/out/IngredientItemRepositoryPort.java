package com.minh.Online.Food.Ordering.domain.ports.out;

import com.minh.Online.Food.Ordering.domain.model.IngredientItem;

import java.util.List;
import java.util.Optional;

public interface IngredientItemRepositoryPort {
    IngredientItem save(IngredientItem i);
    Optional<IngredientItem> findByIdAndRestaurantId(Long itemId, Long restaurantId);
    List<IngredientItem> findByRestaurantId(Long restaurantId);
    List<IngredientItem> findByRestaurantIdAndCategoryId(Long restaurantId, Long categoryId);
    void deleteById(Long id);
}
