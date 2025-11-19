package com.minh.Online.Food.Ordering.domain.ports.out;


import com.minh.Online.Food.Ordering.domain.model.IngredientCategory;

import java.util.List;
import java.util.Optional;

public interface IngredientCategoryRepositoryPort {
    IngredientCategory save(IngredientCategory c);
    Optional<IngredientCategory> findByIdAndRestaurantId(Long categoryId, Long restaurantId);
    List<IngredientCategory> findByRestaurantId(Long restaurantId);
    void deleteById(Long id);
}
