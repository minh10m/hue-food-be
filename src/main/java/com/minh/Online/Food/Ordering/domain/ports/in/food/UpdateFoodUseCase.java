package com.minh.Online.Food.Ordering.domain.ports.in.food;

import com.minh.Online.Food.Ordering.domain.model.Food;

import java.util.Optional;

public interface UpdateFoodUseCase { Optional<Food> update(Long ownerId, Long foodId, Food patch); }

