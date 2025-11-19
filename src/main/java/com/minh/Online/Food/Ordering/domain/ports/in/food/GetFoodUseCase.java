package com.minh.Online.Food.Ordering.domain.ports.in.food;

import com.minh.Online.Food.Ordering.domain.model.Food;

import java.util.Optional;

public interface GetFoodUseCase { Optional<Food> getPublic(Long foodId); Optional<Food> getMine(Long ownerId, Long foodId); }

