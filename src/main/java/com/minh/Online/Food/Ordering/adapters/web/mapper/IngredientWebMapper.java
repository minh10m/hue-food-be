package com.minh.Online.Food.Ordering.adapters.web.mapper;


import com.minh.Online.Food.Ordering.adapters.web.dto.IngredientCategoryRequest;
import com.minh.Online.Food.Ordering.adapters.web.dto.IngredientCategoryResponse;
import com.minh.Online.Food.Ordering.adapters.web.dto.IngredientRequest;
import com.minh.Online.Food.Ordering.adapters.web.dto.IngredientResponse;
import com.minh.Online.Food.Ordering.domain.model.IngredientCategory;
import com.minh.Online.Food.Ordering.domain.model.IngredientItem;

public final class IngredientWebMapper {
    private IngredientWebMapper(){}

    // Category
    public static IngredientCategory toDomain(Long restaurantId, IngredientCategoryRequest r){
        return new IngredientCategory(null, restaurantId, r.getName(), r.getDescription(), r.isActive());
    }
    public static IngredientCategory toPatch(Long restaurantId, IngredientCategoryRequest r){
        return new IngredientCategory(null, restaurantId, r.getName(), r.getDescription(), r.isActive());
    }
    public static IngredientCategoryResponse toResponse(IngredientCategory c){
        IngredientCategoryResponse d = new IngredientCategoryResponse();
        d.setId(c.id()); d.setRestaurantId(c.restaurantId());
        d.setName(c.name()); d.setDescription(c.description()); d.setActive(c.active());
        return d;
    }

    // Item
    public static IngredientItem toDomain(Long restaurantId, IngredientRequest r){
        return new IngredientItem(null, restaurantId, r.getCategoryId(), r.getName(), r.getUnit(), r.getPrice(), r.isInStock(), r.isActive());
    }
    public static IngredientItem toPatch(Long restaurantId, IngredientRequest r){
        return new IngredientItem(null, restaurantId, r.getCategoryId(), r.getName(), r.getUnit(), r.getPrice(), r.isInStock(), r.isActive());
    }
    public static IngredientResponse toResponse(IngredientItem i){
        IngredientResponse d = new IngredientResponse();
        d.setId(i.id()); d.setRestaurantId(i.restaurantId()); d.setCategoryId(i.categoryId());
        d.setName(i.name()); d.setUnit(i.unit()); d.setPrice(i.price());
        d.setInStock(i.inStock()); d.setActive(i.active());
        return d;
    }
}

