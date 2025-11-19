package com.minh.Online.Food.Ordering.domain.model;

import java.util.Objects;

public final class FavoriteRestaurant {

    private final Long id;
    private final Long userId;
    private final Long restaurantId;

    public FavoriteRestaurant(Long id, Long userId, Long restaurantId) {
        this.id = id;
        this.userId = userId;
        this.restaurantId = restaurantId;
    }

    public Long id() { return id; }
    public Long userId() { return userId; }
    public Long restaurantId() { return restaurantId; }

    public FavoriteRestaurant withId(Long newId) {
        return new FavoriteRestaurant(newId, userId, restaurantId);
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof FavoriteRestaurant f) && Objects.equals(id, f.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}


