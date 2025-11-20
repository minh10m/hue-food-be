package com.minh.Online.Food.Ordering.domain.ports.in.cart;

import com.minh.Online.Food.Ordering.domain.model.Cart;

import java.math.BigDecimal;

public interface AddItemToCartUseCase {
    Cart add(Long userId, Long foodId, String foodName, BigDecimal unitPrice, int quantity, Long restaurantId);
}
