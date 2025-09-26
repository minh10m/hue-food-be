package com.minh.Online.Food.Ordering.modules.restaurant.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RestaurantResponse {
    private Long id;
    private String name;
    private String description;
    private String cuisineType;
    private String openingHours;
    private String image;
    private boolean open;

    // Thông tin chủ quán tối giản – không lộ password
    private Long ownerId;
    private String ownerName;

    // Địa chỉ tóm tắt (tuỳ chọn)
    private Long addressId;
    private String street;
    private String city;
}
