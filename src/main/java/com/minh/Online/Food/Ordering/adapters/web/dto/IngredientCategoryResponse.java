package com.minh.Online.Food.Ordering.adapters.web.dto;

import lombok.Getter; import lombok.Setter;
@Getter @Setter
public class IngredientCategoryResponse {
    private Long id;
    private Long restaurantId;
    private String name;
    private String description;
    private boolean active;
}
