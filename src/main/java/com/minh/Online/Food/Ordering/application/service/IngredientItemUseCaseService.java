package com.minh.Online.Food.Ordering.application.service;

import com.minh.Online.Food.Ordering.domain.model.IngredientItem;
import com.minh.Online.Food.Ordering.domain.ports.in.ingredient.*;
import com.minh.Online.Food.Ordering.domain.ports.out.IngredientItemRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class IngredientItemUseCaseService implements
        CreateItemUseCase, UpdateItemUseCase, DeleteItemUseCase,
        ListItemsUseCase, GetItemUseCase {

    private final IngredientItemRepositoryPort items;

    public IngredientItemUseCaseService(IngredientItemRepositoryPort items) {
        this.items = items;
    }

    @Override
    @Transactional
    public IngredientItem create(IngredientItem i) {
        if (i.restaurantId() == null) throw new IllegalArgumentException("restaurantId required");
        if (i.categoryId() == null) throw new IllegalArgumentException("categoryId required");
        return items.save(new IngredientItem(
                null, i.restaurantId(), i.categoryId(), i.name(), i.unit(), i.price(), i.inStock(), i.active()
        ));
    }

    @Override
    @Transactional
    public Optional<IngredientItem> update(Long restaurantId, Long itemId, IngredientItem patch) {
        return items.findByIdAndRestaurantId(itemId, restaurantId)
                .map(cur -> items.save(cur.merge(patch)));
    }

    @Override
    @Transactional
    public void delete(Long restaurantId, Long itemId) {
        var it = items.findByIdAndRestaurantId(itemId, restaurantId)
                .orElseThrow(() -> new NoSuchElementException("Item not found"));
        items.deleteById(it.id());
    }

    @Override
    public List<IngredientItem> listByRestaurant(Long restaurantId) {
        return items.findByRestaurantId(restaurantId);
    }

    @Override
    public List<IngredientItem> listByCategory(Long restaurantId, Long categoryId) {
        return items.findByRestaurantIdAndCategoryId(restaurantId, categoryId);
    }

    @Override
    public Optional<IngredientItem> get(Long restaurantId, Long itemId) {
        return items.findByIdAndRestaurantId(itemId, restaurantId);
    }
}
