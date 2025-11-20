package com.minh.Online.Food.Ordering.adapters.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateRestaurantRequest {

    @NotBlank(message = "Tên nhà hàng không được để trống")
    @Size(max = 255)
    private String name;

    @Size(max = 2000)
    private String description;

    @Size(max = 100)
    private String cuisineType;

    @NotBlank(message = "Địa chỉ không được để trống")
    private String street;

    @NotBlank(message = "Thành phố không được để trống")
    private String city;

    @Size(max = 255)
    private String email;

    @Size(max = 20)
    private String mobile;

    @Size(max = 255)
    private String twitter;

    @Size(max = 255)
    private String instagram;

    @Size(max = 255)
    private String openingHours;

    @Size(max = 500)
    private String image;
}

