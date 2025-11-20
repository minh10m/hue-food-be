package com.minh.Online.Food.Ordering.domain.ports.in.cart;

import com.minh.Online.Food.Ordering.domain.model.Cart;

public interface ClearCartUseCase { Cart clear(Long userId); }

