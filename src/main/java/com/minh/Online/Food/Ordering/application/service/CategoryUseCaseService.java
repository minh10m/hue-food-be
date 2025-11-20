package com.minh.Online.Food.Ordering.application.service;

import com.minh.Online.Food.Ordering.domain.model.Category;
import com.minh.Online.Food.Ordering.domain.ports.in.category.*;
import com.minh.Online.Food.Ordering.domain.ports.out.CategoryRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CategoryUseCaseService implements
        CreateCategoryUseCase, UpdateCategoryUseCase, DeleteCategoryUseCase,
        GetCategoryUseCase, ListCategoriesUseCase {

    private final CategoryRepositoryPort repo;

    public CategoryUseCaseService(CategoryRepositoryPort repo) { this.repo = repo; }

    @Override @Transactional
    public Category create(Category c) {
        if (c.restaurantId() == null) throw new IllegalArgumentException("restaurantId required");
        if (c.name() == null || c.name().isBlank()) throw new IllegalArgumentException("name required");
        return repo.save(new Category(null, c.restaurantId(), c.name(), c.active()));
    }

    @Override @Transactional
    public Optional<Category> update(Long restaurantId, Long categoryId, Category patch) {
        return repo.findByIdAndRestaurantId(categoryId, restaurantId)
                .map(cur -> repo.save(cur.merge(patch)));
    }

    @Override @Transactional
    public void delete(Long restaurantId, Long categoryId) {
        var c = repo.findByIdAndRestaurantId(categoryId, restaurantId)
                .orElseThrow(() -> new NoSuchElementException("Category not found"));
        repo.deleteById(c.id());
    }

    @Override
    public Optional<Category> get(Long restaurantId, Long categoryId) {
        return repo.findByIdAndRestaurantId(categoryId, restaurantId);
    }

    @Override
    public List<Category> listByRestaurant(Long restaurantId) {
        return repo.findByRestaurantId(restaurantId);
    }
}
