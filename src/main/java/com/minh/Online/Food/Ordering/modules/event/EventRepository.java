package com.minh.Online.Food.Ordering.modules.event;

import com.minh.Online.Food.Ordering.modules.event.EventStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByStatus(EventStatus status);
    List<Event> findByRestaurant_Id(Long restaurantId);
}
