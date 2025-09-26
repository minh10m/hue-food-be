package com.minh.Online.Food.Ordering.modules.category.repository;

import com.minh.Online.Food.Ordering.modules.category.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    public List<Category> findByRestaurantId(Long restaurantId);
}
