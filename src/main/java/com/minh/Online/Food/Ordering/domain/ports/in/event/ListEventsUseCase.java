package com.minh.Online.Food.Ordering.domain.ports.in.event;

import com.minh.Online.Food.Ordering.domain.model.Event;

import java.time.Instant;
import java.util.List;

public interface ListEventsUseCase {
    List<Event> listPublicUpcoming(Instant from, int page, int size);
    List<Event> listByRestaurant(Long restaurantId);
    List<Event> listByRestaurantInRange(Long restaurantId, Instant from, Instant to);
}
