package com.minh.Online.Food.Ordering.application.service;

import com.minh.Online.Food.Ordering.domain.model.Cart;
import com.minh.Online.Food.Ordering.domain.ports.in.cart.*;
import com.minh.Online.Food.Ordering.domain.ports.out.CartRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class CartUseCaseService implements
        AddItemToCartUseCase, UpdateCartItemUseCase, RemoveCartItemUseCase, ClearCartUseCase, GetMyCartUseCase {

    private final CartRepositoryPort repo;

    public CartUseCaseService(CartRepositoryPort repo) { this.repo = repo; }

    @Override @Transactional
    public Cart add(Long userId, Long foodId, String foodName, BigDecimal unitPrice, int quantity, Long restaurantId) {
        var cart = repo.findByUserId(userId).orElseGet(() -> new Cart(null, userId, java.util.List.of()));
        var updated = cart.addOrIncrease(foodId, foodName, unitPrice, quantity, restaurantId);
        return repo.save(updated);
    }

    @Override @Transactional
    public Cart updateQuantity(Long userId, Long cartItemId, int quantity) {
        var cart = repo.findByUserId(userId).orElseGet(() -> new Cart(null, userId, java.util.List.of()));
        var updated = cart.updateQuantity(cartItemId, quantity);
        return repo.save(updated);
    }

    @Override @Transactional
    public Cart remove(Long userId, Long cartItemId) {
        var cart = repo.findByUserId(userId).orElseThrow(() -> new java.util.NoSuchElementException("Cart not found"));
        var updated = cart.removeItem(cartItemId);
        return repo.save(updated);
    }

    @Override @Transactional
    public Cart clear(Long userId) {
        var cart = repo.findByUserId(userId).orElseGet(() -> new Cart(null, userId, java.util.List.of()));
        var cleared = cart.clear();
        return repo.save(cleared);
    }

    @Override
    public Cart getOrCreate(Long userId) {
        return repo.findByUserId(userId).orElseGet(() -> repo.save(new Cart(null, userId, java.util.List.of())));
    }
}
