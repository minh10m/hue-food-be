package com.minh.Online.Food.Ordering.modules.cart.dto;

import lombok.Data;

@Data
public class UpdateCartItemRequest {

    private Long cartItemId;

    private int quantity;

}
