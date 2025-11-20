package com.minh.Online.Food.Ordering.adapters.web.mapper;


import com.minh.Online.Food.Ordering.adapters.web.dto.CartResponse;
import com.minh.Online.Food.Ordering.domain.model.Cart;

import java.util.stream.Collectors;

public final class CartWebMapper {
    private CartWebMapper(){}

    public static CartResponse toResponse(Cart c){
        CartResponse d = new CartResponse();
        d.setCartId(c.id());
        d.setUserId(c.userId());
        d.setTotal(c.total());
        d.setItems(c.items().stream().map(i -> {
            var x = new CartResponse.Item();
            x.setId(i.id());
            x.setFoodId(i.foodId());
            x.setFoodName(i.foodName());
            x.setUnitPrice(i.unitPrice());
            x.setQuantity(i.quantity());
            x.setLineTotal(i.lineTotal());
            x.setRestaurantId(i.restaurantId());
            return x;
        }).collect(Collectors.toList()));
        return d;
    }
}

