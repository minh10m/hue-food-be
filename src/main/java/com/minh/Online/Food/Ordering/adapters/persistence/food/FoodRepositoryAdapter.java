package com.minh.Online.Food.Ordering.adapters.persistence.food;

import com.minh.Online.Food.Ordering.domain.model.Food;
import com.minh.Online.Food.Ordering.domain.model.FoodAvailability;
import com.minh.Online.Food.Ordering.domain.ports.out.FoodRepositoryPort;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class FoodRepositoryAdapter implements FoodRepositoryPort {

    private final SpringDataFoodRepository repo;

    public FoodRepositoryAdapter(SpringDataFoodRepository repo) { this.repo = repo; }

    @Override @Transactional
    public Food save(Food f) {
        var e = toEntity(f);
        var s = repo.save(e);
        return toDomain(s);
    }

    @Override public Optional<Food> findById(Long id){ return repo.findById(id).map(this::toDomain); }

    @Override
    public Optional<Food> findByIdAndOwnerId(Long id, Long ownerId) {
        return repo.findByIdAndOwnerId(id, ownerId).map(this::toDomain);
    }

    @Override
    public List<Food> findPublic(Long restaurantId, Long categoryId, int page, int size) {
        return repo.searchPublic(restaurantId, categoryId, FoodAvailability.AVAILABLE, PageRequest.of(page, size))
                .map(this::toDomain).getContent();
    }

    @Override
    public List<Food> findByOwner(Long ownerId) {
        return repo.findByOwner(ownerId).stream().map(this::toDomain).toList();
    }

    @Override
    public List<Food> findByOwnerAndRestaurant(Long ownerId, Long restaurantId) {
        return repo.findByOwnerAndRestaurant(ownerId, restaurantId).stream().map(this::toDomain).toList();
    }

    @Override @Transactional
    public void deleteById(Long id) { repo.deleteById(id); }

    private Food toDomain(FoodJpaEntity e){
        return new Food(
                e.getId(), e.getRestaurantId(), e.getCategoryId(),
                e.getName(), e.getDescription(), e.getPrice(), e.getVegetarian(),
                e.getImageUrl(), e.getAvailability(), e.getCreatedAt(), e.getUpdatedAt()
        );
    }
    private FoodJpaEntity toEntity(Food f){
        return FoodJpaEntity.builder()
                .id(f.id())
                .restaurantId(f.restaurantId())
                .categoryId(f.categoryId())
                .name(f.name())
                .description(f.description())
                .price(f.price())
                .vegetarian(f.vegetarian())
                .imageUrl(f.imageUrl())
                .availability(f.availability() != null ? f.availability() : FoodAvailability.AVAILABLE)
                .createdAt(f.createdAt())
                .updatedAt(f.updatedAt())
                .build();
    }
}

