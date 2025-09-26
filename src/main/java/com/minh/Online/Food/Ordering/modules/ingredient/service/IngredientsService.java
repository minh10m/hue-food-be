package com.minh.Online.Food.Ordering.modules.ingredient.service;

import com.minh.Online.Food.Ordering.modules.ingredient.model.IngredientCategory;
import com.minh.Online.Food.Ordering.modules.ingredient.model.IngredientsItem;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

@CacheConfig(cacheNames = "ingredients")
public interface IngredientsService {

    @CacheEvict(key = "'restaurant_categories_' + #restaurantId")
    public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception;

    @Cacheable(key = "'category_' + #id")
    public IngredientCategory findIngredientCategoryById(Long id) throws Exception;

    @Cacheable(key = "'restaurant_categories_' + #id")
    public List<IngredientCategory> findIngredientCategoriesByRestaurantId(Long id) throws Exception;

    @CacheEvict(key = "'restaurant_' + #restaurantId")
    public IngredientsItem createIngredientsItem(Long restaurantId, String ingredientName , Long categoryId) throws Exception;

    @Cacheable(key = "'restaurant_' + #restaurantId")
    public List<IngredientsItem> findRestaurantIngredients(Long restaurantId) throws Exception;

    @CacheEvict(key = "'restaurant_' + #id")
    public IngredientsItem updateStock(Long id) throws Exception;

}
