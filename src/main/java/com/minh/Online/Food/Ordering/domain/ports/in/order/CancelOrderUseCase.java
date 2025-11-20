package com.minh.Online.Food.Ordering.domain.ports.in.order;

import com.minh.Online.Food.Ordering.domain.model.Order;

public interface CancelOrderUseCase {
    Order cancel(Long userId, Long orderId, String reason);
}
