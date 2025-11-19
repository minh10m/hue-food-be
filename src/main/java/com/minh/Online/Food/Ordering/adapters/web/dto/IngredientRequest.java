package com.minh.Online.Food.Ordering.adapters.web.dto;

import jakarta.validation.constraints.*; import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class IngredientRequest {
    @NotNull private Long categoryId;
    @NotBlank @Size(max=255) private String name;
    @Size(max=32) private String unit;                  // "kg","pcs"...
    @DecimalMin(value = "0.0", inclusive = true) private BigDecimal price;
    private boolean inStock = true;
    private boolean active = true;
}

