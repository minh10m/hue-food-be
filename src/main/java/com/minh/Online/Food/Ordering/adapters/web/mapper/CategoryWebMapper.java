package com.minh.Online.Food.Ordering.adapters.web.mapper;


import com.minh.Online.Food.Ordering.adapters.web.dto.CategoryResponse;
import com.minh.Online.Food.Ordering.adapters.web.dto.CreateCategoryRequest;
import com.minh.Online.Food.Ordering.domain.model.Category;

public final class CategoryWebMapper {
    private CategoryWebMapper(){}

    public static Category toDomain(CreateCategoryRequest r) {
        return new Category(null, r.getRestaurantId(), r.getName(), r.isActive());
    }
    public static Category toPatch(CreateCategoryRequest r) {
        return new Category(null, r.getRestaurantId(), r.getName(), r.isActive());
    }
    public static CategoryResponse toResponse(Category c) {
        CategoryResponse d = new CategoryResponse();
        d.setId(c.id());
        d.setRestaurantId(c.restaurantId());
        d.setName(c.name());
        d.setActive(c.active());
        return d;
    }
}

