package com.minh.Online.Food.Ordering.modules.ingredient.dto;

import lombok.Data;

@Data
public class IngredientRequest {
    private String name;
    private Long categoryId;
    private Long restaurantId;

}
