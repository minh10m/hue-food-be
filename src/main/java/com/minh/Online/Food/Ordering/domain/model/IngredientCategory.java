package com.minh.Online.Food.Ordering.domain.model;

import java.util.Objects;

public final class IngredientCategory {
    private final Long id;
    private final Long restaurantId;
    private final String name;
    private final String description;
    private final boolean active;

    public IngredientCategory(Long id, Long restaurantId, String name, String description, boolean active) {
        this.id = id; this.restaurantId = restaurantId; this.name = name; this.description = description; this.active = active;
    }

    public Long id() { return id; }
    public Long restaurantId() { return restaurantId; }
    public String name() { return name; }
    public String description() { return description; }
    public boolean active() { return active; }

    public IngredientCategory withId(Long newId){ return new IngredientCategory(newId, restaurantId, name, description, active); }
    public IngredientCategory merge(IngredientCategory patch){
        return new IngredientCategory(id, restaurantId,
                patch.name != null ? patch.name : name,
                patch.description != null ? patch.description : description,
                patch.active || active);
    }

    @Override public boolean equals(Object o){ return (o instanceof IngredientCategory c) && Objects.equals(id, c.id); }
    @Override public int hashCode(){ return Objects.hashCode(id); }
}
