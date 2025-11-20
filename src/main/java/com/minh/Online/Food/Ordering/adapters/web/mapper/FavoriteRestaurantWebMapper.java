package com.minh.Online.Food.Ordering.adapters.web.mapper;


import com.minh.Online.Food.Ordering.adapters.web.dto.AddFavoriteRestaurantRequest;

public final class FavoriteRestaurantWebMapper {

    private FavoriteRestaurantWebMapper() {}

    public static Long toRestaurantId(AddFavoriteRestaurantRequest req) {
        return req.getRestaurantId();
    }
}

