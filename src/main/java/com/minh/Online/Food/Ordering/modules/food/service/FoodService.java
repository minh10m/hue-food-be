package com.minh.Online.Food.Ordering.modules.food.service;

import com.minh.Online.Food.Ordering.modules.category.model.Category;
import com.minh.Online.Food.Ordering.modules.food.dto.CreateFoodRequest;
import com.minh.Online.Food.Ordering.modules.food.model.Food;
import com.minh.Online.Food.Ordering.modules.restaurant.Restaurant;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

@CacheConfig(cacheNames = "foods")
public interface FoodService {
    public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant);

    @CacheEvict(key = "'food_' + #foodId")
    void deleteFood(Long foodId) throws Exception;

    @Cacheable(key = "'restaurant_' + #restaurantId + '_' + #isVegetarian + '_' + #isNonVeg + '_' + #seasonal + '_' + (#foodCategory != null ? #foodCategory : 'all')")
    public List<Food> getRestaurantsFood(Long restaurantId,
                                       boolean isVegetarian,
                                       boolean isNonVeg,
                                       boolean seasonal,
                                       String foodCategory);

    @Cacheable(key = "'search_' + #keyword")
    public List<Food> searchFood(String keyword);

    @Cacheable(key = "'food_' + #id")
    public Food findFoodById(Long id) throws Exception;

    @CacheEvict(key = "'food_' + #foodId")
    public Food updateAvailibilityStatus(Long foodId) throws Exception;
}
