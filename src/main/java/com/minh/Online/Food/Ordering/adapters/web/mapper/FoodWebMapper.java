package com.minh.Online.Food.Ordering.adapters.web.mapper;


import com.minh.Online.Food.Ordering.adapters.web.dto.CreateFoodRequest;
import com.minh.Online.Food.Ordering.adapters.web.dto.FoodResponse;
import com.minh.Online.Food.Ordering.domain.model.Food;

import java.time.Instant;

public final class FoodWebMapper {
    private FoodWebMapper(){}

    public static Food toDomain(Long restaurantId, CreateFoodRequest r){
        return new Food(
                null, restaurantId, r.getCategoryId(),
                r.getName(), r.getDescription(), r.getPrice(),
                r.getVegetarian(), r.getImageUrl(),
                null, Instant.now(), Instant.now()
        );
    }

    public static Food toPatch(Long restaurantId, CreateFoodRequest r){
        return new Food(
                null, restaurantId, r.getCategoryId(),
                r.getName(), r.getDescription(), r.getPrice(),
                r.getVegetarian(), r.getImageUrl(),
                null, null, null
        );
    }

    public static FoodResponse toResponse(Food f){
        FoodResponse d = new FoodResponse();
        d.setId(f.id());
        d.setRestaurantId(f.restaurantId());
        d.setCategoryId(f.categoryId());
        d.setName(f.name());
        d.setDescription(f.description());
        d.setPrice(f.price());
        d.setVegetarian(f.vegetarian());
        d.setImageUrl(f.imageUrl());
        d.setAvailability(f.availability() != null ? f.availability().name() : null);
        d.setCreatedAt(f.createdAt());
        d.setUpdatedAt(f.updatedAt());
        return d;
    }
}

