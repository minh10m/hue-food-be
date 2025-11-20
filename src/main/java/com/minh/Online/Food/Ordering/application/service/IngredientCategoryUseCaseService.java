package com.minh.Online.Food.Ordering.application.service;

import com.minh.Online.Food.Ordering.domain.model.IngredientCategory;
import com.minh.Online.Food.Ordering.domain.ports.in.ingredient.*;
import com.minh.Online.Food.Ordering.domain.ports.out.IngredientCategoryRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class IngredientCategoryUseCaseService implements
        CreateIngredientCategoryUseCase, UpdateIngredientCategoryUseCase, DeleteIngredientCategoryUseCase,
        ListIngredinetCategoriesUseCase, GetIngredientCategoryUseCase {

    private final IngredientCategoryRepositoryPort categories;

    public IngredientCategoryUseCaseService(IngredientCategoryRepositoryPort categories) {
        this.categories = categories;
    }

    @Override
    @Transactional
    public IngredientCategory create(IngredientCategory c) {
        if (c.restaurantId() == null) throw new IllegalArgumentException("restaurantId required");
        return categories.save(new IngredientCategory(
                null, c.restaurantId(), c.name(), c.description(), c.active()
        ));
    }

    @Override
    @Transactional
    public Optional<IngredientCategory> update(Long restaurantId, Long categoryId, IngredientCategory patch) {
        return categories.findByIdAndRestaurantId(categoryId, restaurantId)
                .map(cur -> categories.save(cur.merge(patch)));
    }

    @Override
    @Transactional
    public void delete(Long restaurantId, Long categoryId) {
        IngredientCategory c = categories.findByIdAndRestaurantId(categoryId, restaurantId)
                .orElseThrow(() -> new NoSuchElementException("Category not found"));
        categories.deleteById(c.id());
    }

    @Override
    public List<IngredientCategory> listByRestaurant(Long restaurantId) {
        return categories.findByRestaurantId(restaurantId);
    }

    @Override
    public Optional<IngredientCategory> get(Long restaurantId, Long categoryId) {
        return categories.findByIdAndRestaurantId(categoryId, restaurantId);
    }
}
