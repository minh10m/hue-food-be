package com.minh.Online.Food.Ordering.domain.ports.in.restaurant;

import com.minh.Online.Food.Ordering.domain.model.Restaurant;

import java.util.Optional;

public interface UpdateRestaurantUseCase { Optional<Restaurant> update(Long ownerId, Long restaurantId, Restaurant patch); }
