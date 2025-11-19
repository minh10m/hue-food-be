package com.minh.Online.Food.Ordering.domain.model;

import java.math.BigDecimal;
import java.util.Objects;

public final class CartItem {
    private final Long id;
    private final Long foodId;
    private final String foodName;        // snapshot tên món
    private final BigDecimal unitPrice;   // snapshot giá tại thời điểm thêm
    private final int quantity;
    private final Long restaurantId;      // để kiểm soát 1 giỏ/1 nhà hàng nếu cần

    public CartItem(Long id, Long foodId, String foodName, BigDecimal unitPrice, int quantity, Long restaurantId) {
        if (quantity <= 0) throw new IllegalArgumentException("quantity must be > 0");
        this.id = id; this.foodId = foodId; this.foodName = foodName; this.unitPrice = unitPrice; this.quantity = quantity; this.restaurantId = restaurantId;
    }

    public Long id(){ return id; }
    public Long foodId(){ return foodId; }
    public String foodName(){ return foodName; }
    public BigDecimal unitPrice(){ return unitPrice; }
    public int quantity(){ return quantity; }
    public Long restaurantId(){ return restaurantId; }

    public BigDecimal lineTotal(){ return unitPrice.multiply(BigDecimal.valueOf(quantity)); }

    public CartItem withId(Long newId){ return new CartItem(newId, foodId, foodName, unitPrice, quantity, restaurantId); }
    public CartItem withQuantity(int newQty){ return new CartItem(id, foodId, foodName, unitPrice, newQty, restaurantId); }

    @Override public boolean equals(Object o){ return (o instanceof CartItem i) && Objects.equals(id, i.id); }
    @Override public int hashCode(){ return Objects.hashCode(id); }
}

