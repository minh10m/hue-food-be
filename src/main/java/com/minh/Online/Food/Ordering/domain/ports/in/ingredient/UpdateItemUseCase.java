package com.minh.Online.Food.Ordering.domain.ports.in.ingredient;

import com.minh.Online.Food.Ordering.domain.model.IngredientItem;

import java.util.Optional;

public interface UpdateItemUseCase { Optional<IngredientItem> update(Long restaurantId, Long itemId, IngredientItem patch); }