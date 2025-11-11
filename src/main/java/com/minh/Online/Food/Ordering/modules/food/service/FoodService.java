package com.minh.Online.Food.Ordering.modules.food.service;

import com.minh.Online.Food.Ordering.modules.category.Category;
import com.minh.Online.Food.Ordering.modules.food.dto.CreateFoodRequest;
import com.minh.Online.Food.Ordering.modules.food.Food;
import com.minh.Online.Food.Ordering.modules.restaurant.Restaurant;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface FoodService {
    public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant);

    void deleteFood(Long foodId) throws Exception;

    public List<Food> getRestaurantsFood(Long restaurantId,
                                       boolean isVegetarian,
                                       boolean isNonVeg,
                                       boolean seasonal,
                                       String foodCategory);

    public List<Food> searchFood(String keyword);

    public Food findFoodById(Long id) throws Exception;

    public Food updateAvailibilityStatus(Long foodId) throws Exception;
}
