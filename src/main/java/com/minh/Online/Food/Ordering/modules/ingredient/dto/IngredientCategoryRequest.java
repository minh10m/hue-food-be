package com.minh.Online.Food.Ordering.modules.ingredient.dto;

import lombok.Data;

@Data
public class IngredientCategoryRequest {

    private String name;
    private Long restaurantId;
}
