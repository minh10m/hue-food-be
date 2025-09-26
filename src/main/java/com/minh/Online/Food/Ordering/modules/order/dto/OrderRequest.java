package com.minh.Online.Food.Ordering.modules.order.dto;

import com.minh.Online.Food.Ordering.modules.address.dto.AddressRequest;
import lombok.Data;

@Data
public class OrderRequest {

    private Long restaurantId;

    private AddressRequest deliveryAddress;
}
