package com.minh.Online.Food.Ordering.domain.ports.in.ingredient;

import com.minh.Online.Food.Ordering.domain.model.IngredientCategory;

import java.util.List;

public interface ListIngredinetCategoriesUseCase { List<IngredientCategory> listByRestaurant(Long restaurantId); }
