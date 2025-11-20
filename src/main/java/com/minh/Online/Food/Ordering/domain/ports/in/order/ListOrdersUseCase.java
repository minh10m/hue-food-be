package com.minh.Online.Food.Ordering.domain.ports.in.order;

import com.minh.Online.Food.Ordering.domain.model.Order;

import java.time.Instant;
import java.util.List;

public interface ListOrdersUseCase {
    List<Order> listForUser(Long userId, int page, int size);
    List<Order> listForRestaurant(Long restaurantId, int page, int size, Instant from, Instant to);
}
