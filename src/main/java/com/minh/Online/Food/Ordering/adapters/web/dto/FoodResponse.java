package com.minh.Online.Food.Ordering.adapters.web.dto;

import lombok.Getter; import lombok.Setter;
import java.math.BigDecimal;
import java.time.Instant;

@Getter @Setter
public class FoodResponse {
    private Long id;
    private Long restaurantId;
    private Long categoryId;
    private String name;
    private String description;
    private BigDecimal price;
    private Boolean vegetarian;
    private String imageUrl;
    private String availability;
    private Instant createdAt;
    private Instant updatedAt;
}

