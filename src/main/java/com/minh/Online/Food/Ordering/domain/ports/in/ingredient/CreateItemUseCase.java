package com.minh.Online.Food.Ordering.domain.ports.in.ingredient;

import com.minh.Online.Food.Ordering.domain.model.IngredientItem;

public interface CreateItemUseCase { IngredientItem create(IngredientItem i); }
