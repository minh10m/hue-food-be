package com.minh.Online.Food.Ordering.domain.ports.in.restaurant;

public interface DeleteRestaurantUseCase { void delete(Long ownerId, Long restaurantId); }