package com.minh.Online.Food.Ordering.adapters.persistence.Ingredient;

import com.minh.Online.Food.Ordering.adapters.persistence.restaurant.RestaurantJpaEntity;
import com.minh.Online.Food.Ordering.domain.model.IngredientCategory;
import com.minh.Online.Food.Ordering.domain.ports.out.IngredientCategoryRepositoryPort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
public class IngredientCategoryRepositoryAdapter implements IngredientCategoryRepositoryPort {

    private final SpringDataIngredientCategoryRepository repo;

    public IngredientCategoryRepositoryAdapter(SpringDataIngredientCategoryRepository repo) { this.repo = repo; }

    @Override @Transactional
    public IngredientCategory save(IngredientCategory c) {
        IngredientCategoryJpaEntity e = toEntity(c);
        if (c.restaurantId() != null && (e.getRestaurant() == null || e.getRestaurant().getId() == null)) {
            e.setRestaurant(RestaurantJpaEntity.builder().id(c.restaurantId()).build());
        }
        var s = repo.save(e);
        return toDomain(s);
    }

    @Override
    public Optional<IngredientCategory> findByIdAndRestaurantId(Long categoryId, Long restaurantId) {
        return repo.findByIdAndRestaurantId(categoryId, restaurantId).map(this::toDomain);
    }

    @Override
    public List<IngredientCategory> findByRestaurantId(Long restaurantId) {
        return repo.findByRestaurantId(restaurantId).stream().map(this::toDomain).toList();
    }

    @Override @Transactional
    public void deleteById(Long id) { repo.deleteById(id); }

    private IngredientCategory toDomain(IngredientCategoryJpaEntity e){
        return new IngredientCategory(
                e.getId(),
                e.getRestaurant() != null ? e.getRestaurant().getId() : null,
                e.getName(),
                e.getDescription(),
                e.isActive()
        );
    }
    private IngredientCategoryJpaEntity toEntity(IngredientCategory c){
        return IngredientCategoryJpaEntity.builder()
                .id(c.id())
                .restaurant(c.restaurantId() == null ? null : RestaurantJpaEntity.builder().id(c.restaurantId()).build())
                .name(c.name())
                .description(c.description())
                .active(c.active())
                .build();
    }
}

