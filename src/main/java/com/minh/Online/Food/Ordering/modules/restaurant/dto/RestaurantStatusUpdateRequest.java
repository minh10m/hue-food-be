package com.minh.Online.Food.Ordering.modules.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantStatusUpdateRequest {
    private boolean open;
}
