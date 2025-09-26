package com.minh.Online.Food.Ordering.modules.category.service;

import com.minh.Online.Food.Ordering.modules.category.repository.CategoryRepository;
import com.minh.Online.Food.Ordering.modules.category.model.Category;
import com.minh.Online.Food.Ordering.modules.restaurant.Restaurant;
import com.minh.Online.Food.Ordering.modules.restaurant.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category createCategory(String name, Long userId) throws Exception {

        Restaurant restaurant =  restaurantService.getRestaurantByUserId(userId);
        Category category = new Category();
        category.setName(name);
        category.setRestaurant(restaurant);

        return categoryRepository.save(category);
    }

    @Override
    public List<Category> findCategoryByRestaurantId(Long id) throws Exception {
//        Restaurant restaurant = restaurantService.getRestaurantByUserId(id);
        return categoryRepository.findByRestaurantId(id);
    }

    @Override
    public Category findCategoryById(Long Id) throws Exception {
        Optional<Category> optionalCategory = categoryRepository.findById(Id);

        if(optionalCategory.isEmpty()){
            throw new Exception("Category not found");
        }

        return optionalCategory.get();
    }
}
