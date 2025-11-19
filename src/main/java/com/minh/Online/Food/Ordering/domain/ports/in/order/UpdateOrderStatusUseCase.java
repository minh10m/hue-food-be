package com.minh.Online.Food.Ordering.domain.ports.in.order;

import com.minh.Online.Food.Ordering.domain.model.Order;
import com.minh.Online.Food.Ordering.domain.model.OrderStatus;

public interface UpdateOrderStatusUseCase {
    Order updateStatus(Long restaurantId, Long orderId, OrderStatus status);
}
