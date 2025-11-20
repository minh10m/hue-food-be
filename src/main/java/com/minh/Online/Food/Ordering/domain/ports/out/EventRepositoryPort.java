package com.minh.Online.Food.Ordering.domain.ports.out;


import com.minh.Online.Food.Ordering.domain.model.Event;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface EventRepositoryPort {
    Event save(Event e);
    Optional<Event> findById(Long id);
    Optional<Event> findByIdAndRestaurantId(Long id, Long restaurantId);
    List<Event> findUpcomingPublic(Instant from, int page, int size);
    List<Event> findByRestaurant(Long restaurantId);
    List<Event> findByRestaurantInRange(Long restaurantId, Instant from, Instant to);
    void deleteById(Long id);
}

