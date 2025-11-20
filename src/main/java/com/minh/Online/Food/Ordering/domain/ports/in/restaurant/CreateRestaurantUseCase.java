package com.minh.Online.Food.Ordering.domain.ports.in.restaurant;

import com.minh.Online.Food.Ordering.domain.model.Restaurant;

public interface CreateRestaurantUseCase {
    Restaurant create(Restaurant r);
}
