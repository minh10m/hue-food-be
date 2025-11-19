package com.minh.Online.Food.Ordering.domain.ports.in.food;

import com.minh.Online.Food.Ordering.domain.model.Food;

import java.util.List;

public interface ListFoodsUseCase {
    List<Food> listPublic(Long restaurantId, Long categoryId, int page, int size);
    List<Food> listMine(Long ownerId);
    List<Food> listByRestaurant(Long ownerId, Long restaurantId);
}