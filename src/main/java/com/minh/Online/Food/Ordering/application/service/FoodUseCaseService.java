package com.minh.Online.Food.Ordering.application.service;

import com.minh.Online.Food.Ordering.domain.model.Food;
import com.minh.Online.Food.Ordering.domain.model.FoodAvailability;
import com.minh.Online.Food.Ordering.domain.ports.in.food.*;
import com.minh.Online.Food.Ordering.domain.ports.out.FoodRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class FoodUseCaseService implements
        CreateFoodUseCase, UpdateFoodUseCase, UpdateFoodAvailabilityUseCase,
        DeleteFoodUseCase, GetFoodUseCase, ListFoodsUseCase {

    private final FoodRepositoryPort repo;

    public FoodUseCaseService(FoodRepositoryPort repo) { this.repo = repo; }

    @Override @Transactional
    public Food create(Food f) {
        if (f.restaurantId() == null) throw new IllegalArgumentException("restaurantId required");
        if (f.name() == null || f.name().isBlank()) throw new IllegalArgumentException("name required");
        if (f.price() == null) throw new IllegalArgumentException("price required");

        var now = Instant.now();
        var toSave = new Food(
                null, f.restaurantId(), f.categoryId(), f.name(), f.description(),
                f.price(), f.vegetarian(), f.imageUrl(),
                f.availability() != null ? f.availability() : FoodAvailability.AVAILABLE,
                now, now
        );
        return repo.save(toSave);
    }

    @Override @Transactional
    public Optional<Food> update(Long ownerId, Long foodId, Food patch) {
        var now = Instant.now();
        return repo.findByIdAndOwnerId(foodId, ownerId)
                .map(cur -> repo.save(cur.merge(patch, now)));
    }

    @Override @Transactional
    public Food updateAvailability(Long ownerId, Long foodId, FoodAvailability availability) {
        var now = Instant.now();
        var f = repo.findByIdAndOwnerId(foodId, ownerId)
                .orElseThrow(() -> new NoSuchElementException("Food not found"));
        return repo.save(f.withAvailability(availability, now));
    }

    @Override @Transactional
    public void delete(Long ownerId, Long foodId) {
        var f = repo.findByIdAndOwnerId(foodId, ownerId)
                .orElseThrow(() -> new NoSuchElementException("Food not found"));
        repo.deleteById(f.id());
    }

    @Override public Optional<Food> getPublic(Long foodId){ return repo.findById(foodId); }
    @Override public Optional<Food> getMine(Long ownerId, Long foodId){ return repo.findByIdAndOwnerId(foodId, ownerId); }

    @Override
    public List<Food> listPublic(Long restaurantId, Long categoryId, int page, int size) {
        return repo.findPublic(restaurantId, categoryId, page, size);
    }

    @Override
    public List<Food> listMine(Long ownerId) { return repo.findByOwner(ownerId); }

    @Override
    public List<Food> listByRestaurant(Long ownerId, Long restaurantId) {
        return repo.findByOwnerAndRestaurant(ownerId, restaurantId);
    }
}

