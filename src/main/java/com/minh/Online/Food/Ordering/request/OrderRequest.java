package com.minh.Online.Food.Ordering.request;

import com.minh.Online.Food.Ordering.model.Address;
import lombok.Data;

@Data
public class OrderRequest {

    private Long restaurantId;

    private Address deliveryAddress;
}
