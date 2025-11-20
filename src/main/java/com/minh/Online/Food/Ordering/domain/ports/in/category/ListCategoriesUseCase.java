package com.minh.Online.Food.Ordering.domain.ports.in.category;

import com.minh.Online.Food.Ordering.domain.model.Category;

import java.util.List;

public interface ListCategoriesUseCase { List<Category> listByRestaurant(Long restaurantId); }
