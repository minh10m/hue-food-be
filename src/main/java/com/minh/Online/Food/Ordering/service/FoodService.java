package com.minh.Online.Food.Ordering.service;

import com.minh.Online.Food.Ordering.model.Category;
import com.minh.Online.Food.Ordering.model.Food;
import com.minh.Online.Food.Ordering.model.Restaurant;
import com.minh.Online.Food.Ordering.request.CreateFoodRequest;
import org.hibernate.dialect.function.AggregateWindowEmulationQueryTransformer;

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
