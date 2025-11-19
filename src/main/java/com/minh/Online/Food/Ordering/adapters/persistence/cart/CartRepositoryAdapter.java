package com.minh.Online.Food.Ordering.adapters.persistence.cart;

import com.minh.Online.Food.Ordering.domain.model.Cart;
import com.minh.Online.Food.Ordering.domain.model.CartItem;
import com.minh.Online.Food.Ordering.domain.ports.out.CartRepositoryPort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class CartRepositoryAdapter implements CartRepositoryPort {

    private final SpringDataCartRepository repo;

    public CartRepositoryAdapter(SpringDataCartRepository repo) { this.repo = repo; }

    @Override @Transactional
    public Cart save(Cart cart) {
        CartJpaEntity e = toEntity(cart);
        // merge strategy: nếu có id thì load hiện trạng rồi sync items
        CartJpaEntity target = (e.getId() != null)
                ? repo.findById(e.getId()).orElse(new CartJpaEntity())
                : repo.findByUserId(e.getUserId()).orElse(new CartJpaEntity());

        target.setUserId(e.getUserId());
        // đồng bộ items (orphanRemoval = true)
        target.getItems().clear();
        for (var it : e.getItems()){
            it.setCart(target);
            target.getItems().add(it);
        }
        CartJpaEntity saved = repo.save(target);
        return toDomainWithIds(saved);
    }

    @Override
    public Optional<Cart> findByUserId(Long userId) {
        return repo.findWithItemsByUserId(userId).map(this::toDomainWithIds);
    }

    @Override @Transactional
    public void deleteItemById(Long cartId, Long itemId) {
        var cart = repo.findById(cartId).orElseThrow();
        cart.getItems().removeIf(i -> Objects.equals(i.getId(), itemId));
        repo.save(cart);
    }

    @Override @Transactional
    public void clearItems(Long cartId) {
        var cart = repo.findById(cartId).orElseThrow();
        cart.getItems().clear();
        repo.save(cart);
    }

    // ===== Mapping =====
    private Cart toDomainWithIds(CartJpaEntity e){
        var items = e.getItems().stream()
                .map(i -> new CartItem(i.getId(), i.getFoodId(), i.getFoodName(), i.getUnitPrice(), i.getQuantity(), i.getRestaurantId()))
                .collect(Collectors.toList());
        return new Cart(e.getId(), e.getUserId(), items);
    }

    private CartJpaEntity toEntity(Cart c){
        var e = CartJpaEntity.builder().id(c.id()).userId(c.userId()).items(new java.util.ArrayList<>()).build();
        for (CartItem it : c.items()){
            var ie = CartItemJpaEntity.builder()
                    .id(it.id())
                    .foodId(it.foodId())
                    .foodName(it.foodName())
                    .unitPrice(it.unitPrice())
                    .quantity(it.quantity())
                    .restaurantId(it.restaurantId())
                    .build();
            ie.setCart(e);
            e.getItems().add(ie);
        }
        return e;
    }
}

