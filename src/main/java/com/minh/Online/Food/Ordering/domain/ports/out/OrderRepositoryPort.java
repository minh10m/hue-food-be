package com.minh.Online.Food.Ordering.domain.ports.out;

import com.minh.Online.Food.Ordering.domain.model.Order;

import java.time.Instant;
import java.util.*;

public interface OrderRepositoryPort {
    Order save(Order o);
    Optional<Order> findById(Long id);
    Optional<Order> findByIdAndUserId(Long id, Long userId);
    Optional<Order> findByIdAndRestaurantId(Long id, Long restaurantId);
    List<Order> findByUser(Long userId, int page, int size);
    List<Order> findByRestaurant(Long restaurantId, int page, int size, Instant from, Instant to);
}

