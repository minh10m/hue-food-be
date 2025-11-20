package com.minh.Online.Food.Ordering.domain.ports.out;

import com.minh.Online.Food.Ordering.domain.model.Restaurant;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepositoryPort {
    Restaurant save(Restaurant r);
    Optional<Restaurant> findById(Long id);
    Optional<Restaurant> findByIdAndOwnerId(Long id, Long ownerId);
    List<Restaurant> findPublic(String city, String cuisine, int page, int size);
    List<Restaurant> findByOwner(Long ownerId);
    void deleteById(Long id);
}

