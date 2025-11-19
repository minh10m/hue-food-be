package com.minh.Online.Food.Ordering.domain.ports.in.restaurant;

import com.minh.Online.Food.Ordering.domain.model.Restaurant;

public interface UpdateRestaurantStatusUseCase { Restaurant updateStatus(Long ownerId, Long restaurantId, Restaurant.Status status); }