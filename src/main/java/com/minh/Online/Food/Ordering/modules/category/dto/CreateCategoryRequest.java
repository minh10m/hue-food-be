package com.minh.Online.Food.Ordering.modules.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateCategoryRequest {
    @NotBlank(message = "Category name is required")
    private String name;

    @NotNull(message = "Restaurant ID is required")
    private Long restaurantId;
}
