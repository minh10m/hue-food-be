package com.minh.Online.Food.Ordering.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

public final class Food {
    private final Long id;
    private final Long restaurantId;
    private final Long categoryId;     // category món ăn (nếu có)
    private final String name;
    private final String description;
    private final BigDecimal price;
    private final Boolean vegetarian;
    private final String imageUrl;
    private final FoodAvailability availability;
    private final Instant createdAt;
    private final Instant updatedAt;

    public Food(Long id, Long restaurantId, Long categoryId, String name, String description,
                BigDecimal price, Boolean vegetarian, String imageUrl,
                FoodAvailability availability, Instant createdAt, Instant updatedAt) {
        this.id = id; this.restaurantId = restaurantId; this.categoryId = categoryId; this.name = name;
        this.description = description; this.price = price; this.vegetarian = vegetarian;
        this.imageUrl = imageUrl; this.availability = availability; this.createdAt = createdAt; this.updatedAt = updatedAt;
    }

    public Long id(){ return id; }
    public Long restaurantId(){ return restaurantId; }
    public Long categoryId(){ return categoryId; }
    public String name(){ return name; }
    public String description(){ return description; }
    public BigDecimal price(){ return price; }
    public Boolean vegetarian(){ return vegetarian; }
    public String imageUrl(){ return imageUrl; }
    public FoodAvailability availability(){ return availability; }
    public Instant createdAt(){ return createdAt; }
    public Instant updatedAt(){ return updatedAt; }

    public Food withId(Long newId){ return new Food(newId, restaurantId, categoryId, name, description, price, vegetarian, imageUrl, availability, createdAt, updatedAt); }

    public Food withAvailability(FoodAvailability a, java.time.Instant now) {
        return new Food(id, restaurantId, categoryId, name, description, price, vegetarian, imageUrl, a, createdAt, now);
    }
    public Food merge(Food p, java.time.Instant now){
        return new Food(
                id, restaurantId,
                p.categoryId != null ? p.categoryId : categoryId,
                p.name != null ? p.name : name,
                p.description != null ? p.description : description,
                p.price != null ? p.price : price,
                p.vegetarian != null ? p.vegetarian : vegetarian,
                p.imageUrl != null ? p.imageUrl : imageUrl,
                p.availability != null ? p.availability : availability,
                createdAt, now
        );
    }

    @Override public boolean equals(Object o){ return (o instanceof Food f) && Objects.equals(id, f.id); }
    @Override public int hashCode(){ return Objects.hashCode(id); }
}

