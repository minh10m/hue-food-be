package com.minh.Online.Food.Ordering.modules.category.service;

import com.minh.Online.Food.Ordering.modules.category.Category;
import com.minh.Online.Food.Ordering.modules.user.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

    public Category createCategory(String name, Long restaurantId, User user) throws Exception;

    public List<Category> findCategoryByRestaurantId(Long Id) throws Exception;

    public Category findCategoryById(Long Id) throws Exception;

}
