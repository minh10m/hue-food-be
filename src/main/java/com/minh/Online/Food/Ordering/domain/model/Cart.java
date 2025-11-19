package com.minh.Online.Food.Ordering.domain.model;


import java.math.BigDecimal;
import java.util.*;

public final class Cart {
    private final Long id;
    private final Long userId;
    private final List<CartItem> items;

    public Cart(Long id, Long userId, List<CartItem> items) {
        this.id = id; this.userId = userId; this.items = items != null ? List.copyOf(items) : List.of();
    }

    public Long id(){ return id; }
    public Long userId(){ return userId; }
    public List<CartItem> items(){ return items; }

    public BigDecimal total(){
        return items.stream().map(CartItem::lineTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Cart withId(Long newId){ return new Cart(newId, userId, items); }

    public Cart addOrIncrease(Long foodId, String foodName, BigDecimal price, int qty, Long restaurantId){
        if (qty <= 0) throw new IllegalArgumentException("qty must be > 0");
        var newItems = new ArrayList<CartItem>(items);
        for (int i=0;i<newItems.size();i++){
            var it = newItems.get(i);
            if (Objects.equals(it.foodId(), foodId)){
                newItems.set(i, it.withQuantity(it.quantity()+qty));
                return new Cart(id, userId, newItems);
            }
        }
        newItems.add(new CartItem(null, foodId, foodName, price, qty, restaurantId));
        return new Cart(id, userId, newItems);
    }

    public Cart updateQuantity(Long itemId, int qty){
        if (qty <= 0) throw new IllegalArgumentException("qty must be > 0");
        var newItems = new ArrayList<CartItem>(items);
        for (int i=0;i<newItems.size();i++){
            var it = newItems.get(i);
            if (Objects.equals(it.id(), itemId)){
                newItems.set(i, it.withQuantity(qty));
                return new Cart(id, userId, newItems);
            }
        }
        throw new NoSuchElementException("item not found");
    }

    public Cart removeItem(Long itemId){
        var newItems = new ArrayList<CartItem>(items);
        if (!newItems.removeIf(i -> Objects.equals(i.id(), itemId))){
            throw new NoSuchElementException("item not found");
        }
        return new Cart(id, userId, newItems);
    }

    public Cart clear(){ return new Cart(id, userId, List.of()); }
}

