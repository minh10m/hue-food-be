package com.minh.Online.Food.Ordering.domain.model;

import java.math.BigDecimal;
import java.util.Objects;

public final class IngredientItem {
    private final Long id;
    private final Long restaurantId;
    private final Long categoryId;
    private final String name;
    private final String unit;              // ví dụ: "kg", "g", "pcs"
    private final BigDecimal price;         // giá nhập/bán (tuỳ bạn dùng)
    private final boolean inStock;
    private final boolean active;

    public IngredientItem(Long id, Long restaurantId, Long categoryId, String name,
                          String unit, BigDecimal price, boolean inStock, boolean active) {
        this.id = id; this.restaurantId = restaurantId; this.categoryId = categoryId; this.name = name;
        this.unit = unit; this.price = price; this.inStock = inStock; this.active = active;
    }

    public Long id(){ return id; }
    public Long restaurantId(){ return restaurantId; }
    public Long categoryId(){ return categoryId; }
    public String name(){ return name; }
    public String unit(){ return unit; }
    public BigDecimal price(){ return price; }
    public boolean inStock(){ return inStock; }
    public boolean active(){ return active; }

    public IngredientItem withId(Long newId){ return new IngredientItem(newId, restaurantId, categoryId, name, unit, price, inStock, active); }
    public IngredientItem merge(IngredientItem p){
        return new IngredientItem(
                id, restaurantId, p.categoryId != null ? p.categoryId : categoryId,
                p.name != null ? p.name : name,
                p.unit != null ? p.unit : unit,
                p.price != null ? p.price : price,
                p.inStock || inStock,
                p.active || active
        );
    }

    @Override public boolean equals(Object o){ return (o instanceof IngredientItem x) && Objects.equals(id, x.id); }
    @Override public int hashCode(){ return Objects.hashCode(id); }
}

