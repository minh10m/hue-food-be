package com.minh.Online.Food.Ordering.modules.category.service;

import com.minh.Online.Food.Ordering.modules.category.Category;
import com.minh.Online.Food.Ordering.modules.category.CategoryRepository;
import com.minh.Online.Food.Ordering.modules.restaurant.Restaurant;
import com.minh.Online.Food.Ordering.modules.restaurant.RestaurantRepository;
import com.minh.Online.Food.Ordering.modules.restaurant.service.RestaurantService;
import com.minh.Online.Food.Ordering.modules.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    public Category createCategory(String name, Long restaurantId, User user) throws Exception {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new Exception("Restaurant was not found"));

        // Kiểm tra người tạo có phải owner của nhà hàng không
        if (!restaurant.getOwner().getId().equals(user.getId())) {
            throw new Exception("You do not have permission to create category for this restaurant");
        }

        Category category = new Category();
        category.setName(name);
        category.setRestaurant(restaurant);

        return categoryRepository.save(category);
    }


    @Override
    public List<Category> findCategoryByRestaurantId(Long id) throws Exception {
        List<Category> categories = categoryRepository.findByRestaurantId(id);
        if (categories == null || categories.isEmpty()) {
            return new ArrayList<>(); // Return empty list instead of throwing exception
        }
        return categories;
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
