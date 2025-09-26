package com.minh.Online.Food.Ordering.modules.restaurant.dto;

import com.minh.Online.Food.Ordering.modules.address.Address;
import com.minh.Online.Food.Ordering.modules.address.dto.ContactInformation;
import lombok.Data;

@Data
public class CreateRestaurantRequest {
    private Long id;

    private String name;

    private String description;
    
    private String cuisineType;
    
    private Address address;

    private ContactInformation contactInformation;

    private String openingHours;

    private String image;


}
