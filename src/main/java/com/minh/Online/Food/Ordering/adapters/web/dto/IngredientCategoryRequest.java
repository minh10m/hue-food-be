package com.minh.Online.Food.Ordering.adapters.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter; @Getter
public class IngredientCategoryRequest {
    @NotBlank @Size(max=255) private String name;
    @Size(max=1000) private String description;
    private boolean active = true;
}
