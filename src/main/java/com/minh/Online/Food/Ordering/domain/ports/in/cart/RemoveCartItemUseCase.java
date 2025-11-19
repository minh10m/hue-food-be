package com.minh.Online.Food.Ordering.domain.ports.in.cart;

import com.minh.Online.Food.Ordering.domain.model.Cart;

public interface RemoveCartItemUseCase { Cart remove(Long userId, Long cartItemId); }

