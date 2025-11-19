package com.minh.Online.Food.Ordering.domain.ports.in.order;

import com.minh.Online.Food.Ordering.domain.model.Order;

public interface CreateOrderUseCase {
    Order create(Order draft);
}
