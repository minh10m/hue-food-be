package com.minh.Online.Food.Ordering.modules.restaurant.dto;

import com.minh.Online.Food.Ordering.modules.restaurant.Restaurant;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Embeddable
public class RestaurantDto {
    private Long id;
    private String title;

    @Column(length = 1000)
    private String image;

    private String description;
    private Boolean open;       // add this if you have it
    private String city;        // optional
    private String street;      // optional
    private String openingHours;// optional
}

