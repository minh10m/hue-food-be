package com.minh.Online.Food.Ordering.domain.ports.in.ingredient;

import com.minh.Online.Food.Ordering.domain.model.IngredientItem;

import java.util.Optional;

public interface GetItemUseCase { Optional<IngredientItem> get(Long restaurantId, Long itemId); }
