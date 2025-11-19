package com.minh.Online.Food.Ordering.adapters.persistence.category;

import com.minh.Online.Food.Ordering.domain.model.Category;
import com.minh.Online.Food.Ordering.domain.ports.out.CategoryRepositoryPort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
public class CategoryRepositoryAdapter implements CategoryRepositoryPort {

    private final SpringDataCategoryRepository repo;

    public CategoryRepositoryAdapter(SpringDataCategoryRepository repo) { this.repo = repo; }

    @Override @Transactional
    public Category save(Category c) {
        CategoryJpaEntity e = toEntity(c);
        CategoryJpaEntity s = repo.save(e);
        return toDomain(s);
    }

    @Override
    public Optional<Category> findByIdAndRestaurantId(Long id, Long restaurantId) {
        return repo.findByIdAndRestaurantId(id, restaurantId).map(this::toDomain);
    }

    @Override
    public List<Category> findByRestaurantId(Long restaurantId) {
        return repo.findByRestaurantIdOrderByNameAsc(restaurantId).stream().map(this::toDomain).toList();
    }

    @Override @Transactional
    public void deleteById(Long id) { repo.deleteById(id); }

    private Category toDomain(CategoryJpaEntity e){
        return new Category(e.getId(), e.getRestaurantId(), e.getName(), e.isActive());
    }
    private CategoryJpaEntity toEntity(Category c){
        return CategoryJpaEntity.builder()
                .id(c.id())
                .restaurantId(c.restaurantId())
                .name(c.name())
                .active(c.active())
                .build();
    }
}

