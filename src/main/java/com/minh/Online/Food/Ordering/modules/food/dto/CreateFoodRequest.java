package com.minh.Online.Food.Ordering.modules.food.dto;

import com.minh.Online.Food.Ordering.modules.category.Category;
import com.minh.Online.Food.Ordering.modules.ingredient.model.IngredientsItem;
import lombok.Data;

import java.util.List;

@Data
public class CreateFoodRequest {
    private String name;
    private String description;
    private Long price;

    private Category category;
    private List<String> images;

    private Long restaurantId;
    private boolean vegetarian;
    private boolean seasonal;
    private List<IngredientsItem> ingredients;

}