package com.minh.Online.Food.Ordering.adapters.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter; import lombok.Setter;

@Getter @Setter
public class CreateCategoryRequest {
    @NotNull private Long restaurantId;
    @NotBlank private String name;
    private boolean active = true;
}

