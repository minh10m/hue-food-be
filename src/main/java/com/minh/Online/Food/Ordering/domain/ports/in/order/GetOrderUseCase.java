package com.minh.Online.Food.Ordering.domain.ports.in.order;

import com.minh.Online.Food.Ordering.domain.model.Order;

import java.util.Optional;

public interface GetOrderUseCase {
    Optional<Order> getByIdForUser(Long userId, Long orderId);
    Optional<Order> getByIdForRestaurant(Long restaurantId, Long orderId);
}
