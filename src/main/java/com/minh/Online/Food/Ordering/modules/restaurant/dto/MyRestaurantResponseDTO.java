package com.minh.Online.Food.Ordering.modules.restaurant.dto;

import com.minh.Online.Food.Ordering.modules.address.Address;
import com.minh.Online.Food.Ordering.modules.address.dto.ContactInformation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyRestaurantResponseDTO {
    private Long id;
    private String name;
    private String description;
    private String cuisineType;
    private Address address;
    private ContactInformation contactInformation;
    private String openingHours;
    private String image;
    private LocalDate registrationDate;
    private boolean open;
    private Long ownerId;
    private String ownerFullName;
}
