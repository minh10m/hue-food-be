package com.minh.Online.Food.Ordering.domain.ports.in.cart;

import com.minh.Online.Food.Ordering.domain.model.Cart;

public interface UpdateCartItemUseCase { Cart updateQuantity(Long userId, Long cartItemId, int quantity); }

