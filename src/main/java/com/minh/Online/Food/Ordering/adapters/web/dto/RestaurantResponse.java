package com.minh.Online.Food.Ordering.adapters.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantResponse {
    private Long id;
    private String name;
    private String description;
    private String cuisineType;
    private String street;
    private String city;
    private String openingHours;
    private String image;
    private String status;
}
