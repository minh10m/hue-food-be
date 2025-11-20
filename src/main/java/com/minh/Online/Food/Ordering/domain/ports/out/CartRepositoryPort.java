package com.minh.Online.Food.Ordering.domain.ports.out;


import com.minh.Online.Food.Ordering.domain.model.Cart;

import java.util.Optional;

public interface CartRepositoryPort {
    Cart save(Cart cart);
    Optional<Cart> findByUserId(Long userId);     // load kèm items
    void deleteItemById(Long cartId, Long itemId);
    void clearItems(Long cartId);
}

