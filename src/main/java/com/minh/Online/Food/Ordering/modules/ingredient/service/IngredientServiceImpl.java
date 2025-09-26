package com.minh.Online.Food.Ordering.modules.ingredient.service;

import com.minh.Online.Food.Ordering.modules.ingredient.model.IngredientCategory;
import com.minh.Online.Food.Ordering.modules.ingredient.model.IngredientsItem;
import com.minh.Online.Food.Ordering.modules.ingredient.repository.IngredientCategoryRepository;
import com.minh.Online.Food.Ordering.modules.ingredient.repository.IngredientItemRepository;
import com.minh.Online.Food.Ordering.modules.restaurant.Restaurant;
import com.minh.Online.Food.Ordering.modules.category.repository.CategoryRepository;
import com.minh.Online.Food.Ordering.modules.restaurant.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientServiceImpl implements IngredientsService {

    @Autowired
    private IngredientCategoryRepository ingredientCategoryRepository;

    @Autowired
    private IngredientItemRepository ingredientItemRepository;

    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception {

        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);

        IngredientCategory ingredientCategory = new IngredientCategory();
        ingredientCategory.setName(name);
        ingredientCategory.setRestaurant(restaurant);

        return ingredientCategoryRepository.save(ingredientCategory);
    }

    @Override
    public IngredientCategory findIngredientCategoryById(Long id) throws Exception {

        Optional<IngredientCategory> ingredientCategory = ingredientCategoryRepository.findById(id);

        if (ingredientCategory.isEmpty()){
            throw new Exception("Ingredient Category not found");
        }

        return ingredientCategory.get();
    }

    @Override
    public List<IngredientCategory> findIngredientCategoriesByRestaurantId(Long id) throws Exception {
        restaurantService.findRestaurantById(id);
        return ingredientCategoryRepository.findByRestaurantId(id);
    }

    @Override
    public IngredientsItem createIngredientsItem(Long restaurantId, String ingredientName, Long categoryId) throws Exception {

        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);

        IngredientCategory category = findIngredientCategoryById(categoryId);

        IngredientsItem ingredientsItem = new IngredientsItem();
        ingredientsItem.setRestaurant(restaurant);
        ingredientsItem.setName(ingredientName);
        ingredientsItem.setCategory(category);

        IngredientsItem savedIngredientsItem = ingredientItemRepository.save(ingredientsItem);
        category.getIngredients().add(ingredientsItem);

        return savedIngredientsItem;
    }

    @Override
    public List<IngredientsItem> findRestaurantIngredients(Long restaurantId) throws Exception {
        return ingredientItemRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public IngredientsItem updateStock(Long id) throws Exception {

        Optional<IngredientsItem> optionalIngredientsItem = ingredientItemRepository.findById(id);

        if (optionalIngredientsItem.isEmpty()){
            throw new Exception("Ingredient Item not found");
        }

        IngredientsItem ingredientsItem = optionalIngredientsItem.get();

        ingredientsItem.setInStoke(!ingredientsItem.isInStoke());

        return ingredientItemRepository.save(ingredientsItem);
    }
}
