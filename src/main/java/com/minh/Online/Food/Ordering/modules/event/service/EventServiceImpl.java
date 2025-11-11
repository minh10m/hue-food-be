package com.minh.Online.Food.Ordering.modules.event.service;

import com.minh.Online.Food.Ordering.modules.event.Event;
import com.minh.Online.Food.Ordering.modules.event.EventRepository;
import com.minh.Online.Food.Ordering.modules.event.EventStatus;
import com.minh.Online.Food.Ordering.modules.event.dto.CreateEventRequest;
import com.minh.Online.Food.Ordering.modules.event.dto.EventResponse;
import com.minh.Online.Food.Ordering.modules.restaurant.Restaurant;
import com.minh.Online.Food.Ordering.modules.restaurant.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service @RequiredArgsConstructor @Transactional
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepo;
    private final RestaurantRepository restaurantRepo;

    @Override
    public EventResponse createForRestaurant(Long restaurantId, Long userId, CreateEventRequest req) {
        Restaurant r = restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found: " + restaurantId));

        if (r.getOwner() != null && !r.getOwner().getId().equals(userId)) {
            throw new IllegalStateException("Not allowed to create event for this restaurant");
        }

        if (req.getEndAt().isBefore(req.getStartAt())) {
            throw new IllegalArgumentException("endAt must be after startAt");
        }

        Event e = Event.builder()
                .title(req.getTitle())
                .description(req.getDescription())
                .startAt(req.getStartAt())
                .endAt(req.getEndAt())
                .image(req.getImage()) // 🔹 single
                .status(req.getStatus() == null ? EventStatus.ACTIVE : req.getStatus())
                .restaurant(r)
                .build();

        e = eventRepo.save(e);
        return toResponse(e);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventResponse> getPublicEvents() {
        return eventRepo.findByStatus(EventStatus.ACTIVE).stream().map(this::toResponse).toList();
    }

    @Override
    public void deleteEvent(Long eventId, Long userId) {
        Event e = eventRepo.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found: " + eventId));

        if (e.getRestaurant().getOwner() != null &&
                !e.getRestaurant().getOwner().getId().equals(userId)) {
            throw new IllegalStateException("Not allowed to delete this event");
        }
        eventRepo.delete(e);
    }

    private EventResponse toResponse(Event e) {
        return EventResponse.builder()
                .id(e.getId())
                .title(e.getTitle())
                .description(e.getDescription())
                .startAt(e.getStartAt())
                .endAt(e.getEndAt())
                .image(e.getImage()) // 🔹 single
                .status(e.getStatus())
                .restaurantId(e.getRestaurant().getId())
                .build();
    }
}
