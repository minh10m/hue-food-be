package com.minh.Online.Food.Ordering.domain.ports.out;

import com.minh.Online.Food.Ordering.domain.model.Category;

import java.util.*;

public interface CategoryRepositoryPort {
    Category save(Category c);
    Optional<Category> findByIdAndRestaurantId(Long id, Long restaurantId);
    List<Category> findByRestaurantId(Long restaurantId);
    void deleteById(Long id);
}

