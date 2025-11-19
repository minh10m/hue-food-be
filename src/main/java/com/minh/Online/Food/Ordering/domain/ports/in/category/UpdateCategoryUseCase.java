package com.minh.Online.Food.Ordering.domain.ports.in.category;

import com.minh.Online.Food.Ordering.domain.model.Category;

import java.util.Optional;

public interface UpdateCategoryUseCase { Optional<Category> update(Long restaurantId, Long categoryId, Category patch); }