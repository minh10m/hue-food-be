package com.minh.Online.Food.Ordering.domain.ports.in.restaurant;

import com.minh.Online.Food.Ordering.domain.model.Restaurant;

import java.util.Optional;

public interface GetRestaurantUseCase { Optional<Restaurant> getPublic(Long restaurantId); Optional<Restaurant> getMine(Long ownerId, Long restaurantId); }