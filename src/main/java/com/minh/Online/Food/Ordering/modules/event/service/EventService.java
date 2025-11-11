package com.minh.Online.Food.Ordering.modules.event.service;

import com.minh.Online.Food.Ordering.modules.event.dto.CreateEventRequest;
import com.minh.Online.Food.Ordering.modules.event.dto.EventResponse;

import java.util.List;

public interface EventService {
    EventResponse createForRestaurant(Long restaurantId, Long userId, CreateEventRequest req);
    List<EventResponse> getPublicEvents();            // GET /api/events
    void deleteEvent(Long eventId, Long userId);      // DELETE /api/admin/events/{id}
}
