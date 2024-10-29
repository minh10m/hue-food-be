package com.minh.Online.Food.Ordering.service;

import com.minh.Online.Food.Ordering.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

    public Category createCategory(String name, Long userId) throws Exception;

    public List<Category> findCategoryByRestaurantId(Long Id) throws Exception;

    public Category findCategoryById(Long Id) throws Exception;

}
