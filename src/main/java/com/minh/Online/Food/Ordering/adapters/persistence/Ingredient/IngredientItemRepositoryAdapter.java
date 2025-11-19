package com.minh.Online.Food.Ordering.adapters.persistence.Ingredient;

import com.minh.Online.Food.Ordering.adapters.persistence.restaurant.RestaurantJpaEntity;
import com.minh.Online.Food.Ordering.domain.model.IngredientItem;
import com.minh.Online.Food.Ordering.domain.ports.out.IngredientItemRepositoryPort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
public class IngredientItemRepositoryAdapter implements IngredientItemRepositoryPort {

    private final SpringDataIngredientItemRepository repo;

    public IngredientItemRepositoryAdapter(SpringDataIngredientItemRepository repo){ this.repo = repo; }

    @Override @Transactional
    public IngredientItem save(IngredientItem i) {
        IngredientItemJpaEntity e = toEntity(i);
        if (i.restaurantId() != null && (e.getRestaurant() == null || e.getRestaurant().getId() == null)) {
            e.setRestaurant(RestaurantJpaEntity.builder().id(i.restaurantId()).build());
        }
        if (i.categoryId() != null && (e.getCategory() == null || e.getCategory().getId() == null)) {
            e.setCategory(IngredientCategoryJpaEntity.builder().id(i.categoryId()).build());
        }
        var s = repo.save(e);
        return toDomain(s);
    }

    @Override
    public Optional<IngredientItem> findByIdAndRestaurantId(Long itemId, Long restaurantId) {
        return repo.findByIdAndRestaurantId(itemId, restaurantId).map(this::toDomain);
    }

    @Override
    public List<IngredientItem> findByRestaurantId(Long restaurantId) {
        return repo.findByRestaurantId(restaurantId).stream().map(this::toDomain).toList();
    }

    @Override
    public List<IngredientItem> findByRestaurantIdAndCategoryId(Long restaurantId, Long categoryId) {
        return repo.findByRestaurantIdAndCategoryId(restaurantId, categoryId).stream().map(this::toDomain).toList();
    }

    @Override @Transactional
    public void deleteById(Long id) { repo.deleteById(id); }

    private IngredientItem toDomain(IngredientItemJpaEntity e){
        return new IngredientItem(
                e.getId(),
                e.getRestaurant() != null ? e.getRestaurant().getId() : null,
                e.getCategory() != null ? e.getCategory().getId() : null,
                e.getName(),
                e.getUnit(),
                e.getPrice(),
                e.isInStock(),
                e.isActive()
        );
    }
    private IngredientItemJpaEntity toEntity(IngredientItem i){
        return IngredientItemJpaEntity.builder()
                .id(i.id())
                .restaurant(i.restaurantId() == null ? null : RestaurantJpaEntity.builder().id(i.restaurantId()).build())
                .category(i.categoryId() == null ? null : IngredientCategoryJpaEntity.builder().id(i.categoryId()).build())
                .name(i.name())
                .unit(i.unit())
                .price(i.price())
                .inStock(i.inStock())
                .active(i.active())
                .build();
    }
}

