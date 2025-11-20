package com.minh.Online.Food.Ordering.adapters.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantDto {
    private Long id;
    private String name;
    private String description;
    private String cuisineType;
    private String image;
    private String city;
    private String status;
}

