package com.minh.Online.Food.Ordering.adapters.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class MyRestaurantResponseDTO {
    private Long id;
    private Long ownerId;
    private String name;
    private String description;
    private String cuisineType;
    private String street;
    private String city;
    private String email;
    private String mobile;
    private String twitter;
    private String instagram;
    private String openingHours;
    private String image;
    private String status;
    private Instant createdAt;
    private Instant updatedAt;
}
