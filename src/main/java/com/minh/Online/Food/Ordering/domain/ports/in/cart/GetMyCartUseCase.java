package com.minh.Online.Food.Ordering.domain.ports.in.cart;

import com.minh.Online.Food.Ordering.domain.model.Cart;

public interface GetMyCartUseCase { Cart getOrCreate(Long userId); }

