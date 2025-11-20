package com.minh.Online.Food.Ordering.domain.ports.in.restaurant;

import com.minh.Online.Food.Ordering.domain.model.Restaurant;
import com.minh.Online.Food.Ordering.domain.model.RestaurantStatus;

public interface UpdateRestaurantStatusUseCase { Restaurant updateStatus(Long ownerId, Long restaurantId, RestaurantStatus status); }