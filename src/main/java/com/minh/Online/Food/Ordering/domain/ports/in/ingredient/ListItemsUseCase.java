package com.minh.Online.Food.Ordering.domain.ports.in.ingredient;

import com.minh.Online.Food.Ordering.domain.model.IngredientItem;

import java.util.List;

public interface ListItemsUseCase {
    List<IngredientItem> listByRestaurant(Long restaurantId);
    List<IngredientItem> listByCategory(Long restaurantId, Long categoryId);
}
