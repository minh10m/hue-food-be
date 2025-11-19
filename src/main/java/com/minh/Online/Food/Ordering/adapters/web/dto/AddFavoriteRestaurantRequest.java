package com.minh.Online.Food.Ordering.adapters.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddFavoriteRestaurantRequest {

    @NotNull
    private Long restaurantId;
}

