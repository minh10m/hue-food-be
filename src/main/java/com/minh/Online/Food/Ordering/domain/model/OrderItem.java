package com.minh.Online.Food.Ordering.domain.model;

import java.math.BigDecimal;
import java.util.Objects;

public final class OrderItem {
    private final Long id;
    private final Long foodId;
    private final String foodName;
    private final BigDecimal unitPrice;
    private final int quantity;

    public OrderItem(Long id, Long foodId, String foodName, BigDecimal unitPrice, int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("quantity must be > 0");
        this.id = id; this.foodId = foodId; this.foodName = foodName; this.unitPrice = unitPrice; this.quantity = quantity;
    }

    public Long id(){ return id; }
    public Long foodId(){ return foodId; }
    public String foodName(){ return foodName; }
    public BigDecimal unitPrice(){ return unitPrice; }
    public int quantity(){ return quantity; }

    public BigDecimal lineTotal(){ return unitPrice.multiply(BigDecimal.valueOf(quantity)); }

    public OrderItem withId(Long newId){ return new OrderItem(newId, foodId, foodName, unitPrice, quantity); }

    @Override public boolean equals(Object o){ return (o instanceof OrderItem i) && Objects.equals(id, i.id); }
    @Override public int hashCode(){ return Objects.hashCode(id); }
}
