package com.minh.Online.Food.Ordering.application.service;

import com.minh.Online.Food.Ordering.domain.model.Restaurant;
import com.minh.Online.Food.Ordering.domain.ports.in.restaurant.*;
import com.minh.Online.Food.Ordering.domain.ports.out.RestaurantRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class RestaurantUseCaseService implements
        CreateRestaurantUseCase, UpdateRestaurantUseCase, UpdateRestaurantStatusUseCase,
        DeleteRestaurantUseCase, GetRestaurantUseCase, ListRestaurantsUseCase {

    private final RestaurantRepositoryPort repo;

    public RestaurantUseCaseService(RestaurantRepositoryPort repo) { this.repo = repo; }

    @Override @Transactional
    public Restaurant create(Restaurant r) {
        if (r.ownerId() == null) throw new IllegalArgumentException("ownerId required");
        var now = Instant.now();
        Restaurant toSave = new Restaurant(null, r.ownerId(), r.name(), r.description(), r.cuisineType(),
                r.street(), r.city(), r.email(), r.mobile(), r.twitter(), r.instagram(),
                r.openingHours(), r.image(),
                r.status() != null ? r.status() : Restaurant.Status.CLOSED,
                now, now);
        return repo.save(toSave);
    }

    @Override @Transactional
    public Optional<Restaurant> update(Long ownerId, Long restaurantId, Restaurant patch) {
        var now = Instant.now();
        return repo.findByIdAndOwnerId(restaurantId, ownerId)
                .map(curr -> repo.save(curr.merge(patch, now)));
    }

    @Override @Transactional
    public Restaurant updateStatus(Long ownerId, Long restaurantId, Restaurant.Status status) {
        var now = Instant.now();
        var r = repo.findByIdAndOwnerId(restaurantId, ownerId)
                .orElseThrow(() -> new NoSuchElementException("Restaurant not found"));
        return repo.save(r.withStatus(status, now));
    }

    @Override @Transactional
    public void delete(Long ownerId, Long restaurantId) {
        var r = repo.findByIdAndOwnerId(restaurantId, ownerId)
                .orElseThrow(() -> new NoSuchElementException("Restaurant not found"));
        repo.deleteById(r.id());
    }

    @Override
    public Optional<Restaurant> getPublic(Long restaurantId) { return repo.findById(restaurantId); }

    @Override
    public Optional<Restaurant> getMine(Long ownerId, Long restaurantId) {
        return repo.findByIdAndOwnerId(restaurantId, ownerId);
    }

    @Override
    public List<Restaurant> listPublic(String city, String cuisine, int page, int size) {
        return repo.findPublic(city, cuisine, page, size);
    }

    @Override
    public List<Restaurant> listMine(Long ownerId) { return repo.findByOwner(ownerId); }
}

