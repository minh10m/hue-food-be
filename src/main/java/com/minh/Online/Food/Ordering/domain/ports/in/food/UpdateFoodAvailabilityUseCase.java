package com.minh.Online.Food.Ordering.domain.ports.in.food;

import com.minh.Online.Food.Ordering.domain.model.Food;
import com.minh.Online.Food.Ordering.domain.model.FoodAvailability;

public interface UpdateFoodAvailabilityUseCase { Food updateAvailability(Long ownerId, Long foodId, FoodAvailability availability); }

