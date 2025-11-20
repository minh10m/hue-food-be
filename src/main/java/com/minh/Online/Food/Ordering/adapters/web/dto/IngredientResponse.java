package com.minh.Online.Food.Ordering.adapters.web.dto;

import lombok.Getter; import lombok.Setter;
import java.math.BigDecimal;

@Getter @Setter
public class IngredientResponse {
    private Long id;
    private Long restaurantId;
    private Long categoryId;
    private String name;
    private String unit;
    private BigDecimal price;
    private boolean inStock;
    private boolean active;
}

