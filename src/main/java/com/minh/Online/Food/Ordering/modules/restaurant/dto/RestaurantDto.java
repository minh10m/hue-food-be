package com.minh.Online.Food.Ordering.modules.restaurant.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.util.List;

@Data
@Embeddable
public class RestaurantDto {

    private String title;

    @Column(length = 1000)
    private String image;

    private String description;
    private Long id;
}
