package com.minh.Online.Food.Ordering.service;

import com.minh.Online.Food.Ordering.model.Category;
import com.minh.Online.Food.Ordering.model.IngredientCategory;
import com.minh.Online.Food.Ordering.model.IngredientsItem;

import java.util.List;

public interface IngredientsService {

    public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception;

    public IngredientCategory findIngredientCategoryById(Long id) throws Exception;

    public List<IngredientCategory> findIngredientCategoriesByRestaurantId(Long id) throws Exception;

    public IngredientsItem createIngredientsItem(Long restaurantId, String ingredientName ,Long categoryId) throws Exception;

    public List<IngredientsItem> findRestaurantIngredients(Long restaurantId) throws Exception;

    public IngredientsItem updateStock(Long id) throws Exception;

}
