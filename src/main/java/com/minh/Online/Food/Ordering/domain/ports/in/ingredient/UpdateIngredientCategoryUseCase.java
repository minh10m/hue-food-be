package com.minh.Online.Food.Ordering.domain.ports.in.ingredient;

import com.minh.Online.Food.Ordering.domain.model.IngredientCategory;

import java.util.Optional;

public interface UpdateIngredientCategoryUseCase { Optional<IngredientCategory> update(Long restaurantId, Long categoryId, IngredientCategory patch); }