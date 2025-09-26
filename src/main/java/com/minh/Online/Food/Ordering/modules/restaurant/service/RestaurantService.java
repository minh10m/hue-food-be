package com.minh.Online.Food.Ordering.modules.restaurant.service;

import com.minh.Online.Food.Ordering.modules.restaurant.dto.CreateRestaurantRequest;
import com.minh.Online.Food.Ordering.modules.restaurant.dto.RestaurantDto;
import com.minh.Online.Food.Ordering.modules.restaurant.dto.RestaurantResponse;
import com.minh.Online.Food.Ordering.modules.restaurant.Restaurant;
import com.minh.Online.Food.Ordering.modules.user.User;

import java.util.List;

public interface RestaurantService {
    public Restaurant createRestaurant(CreateRestaurantRequest req, User user);

    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updateReq) throws Exception;

    public void deleteRestaurant(Long restaurantId) throws Exception;

    public List<RestaurantResponse> getAllRestaurantsDto();

    public List<Restaurant> searchRestaurant(String keyWord);

    public Restaurant findRestaurantById(Long restaurantId) throws Exception;

    public Restaurant getRestaurantByUserId(Long userId) throws Exception;

    public RestaurantDto addToFavorites(Long restaurantId, User user) throws Exception;

    public Restaurant updateRestaurantStatus(Long restaurantId) throws Exception;
}
