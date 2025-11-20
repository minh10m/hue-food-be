package com.minh.Online.Food.Ordering.domain.model;

import java.util.Objects;

public final class Category {
    private final Long id;
    private final Long restaurantId;
    private final String name;
    private final boolean active;

    public Category(Long id, Long restaurantId, String name, boolean active) {
        this.id = id; this.restaurantId = restaurantId; this.name = name; this.active = active;
    }
    public Long id(){ return id; }
    public Long restaurantId(){ return restaurantId; }
    public String name(){ return name; }
    public boolean active(){ return active; }

    public Category withId(Long newId){ return new Category(newId, restaurantId, name, active); }
    public Category merge(Category patch){
        return new Category(id, restaurantId,
                patch.name != null ? patch.name : name,
                patch.active || active);
    }
    @Override public boolean equals(Object o){ return (o instanceof Category c) && Objects.equals(id, c.id); }
    @Override public int hashCode(){ return Objects.hashCode(id); }
}

