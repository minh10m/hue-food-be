package com.minh.Online.Food.Ordering.adapters.web.mapper;

import com.minh.Online.Food.Ordering.adapters.web.dto.CreateEventRequest;
import com.minh.Online.Food.Ordering.adapters.web.dto.EventResponse;
import com.minh.Online.Food.Ordering.domain.model.Event;

import java.time.Instant;

public final class EventWebMapper {
    private EventWebMapper(){}

    public static Event toDomain(Long restaurantId, CreateEventRequest r){
        return new Event(
                null, restaurantId,
                r.getTitle(), r.getDescription(),
                r.getStartsAt(), r.getEndsAt(), r.getImageUrl(),
                null, Instant.now(), Instant.now()
        );
    }

    public static Event toPatch(Long restaurantId, CreateEventRequest r){
        return new Event(
                null, restaurantId,
                r.getTitle(), r.getDescription(),
                r.getStartsAt(), r.getEndsAt(), r.getImageUrl(),
                null, null, null
        );
    }

    public static EventResponse toResponse(Event e){
        EventResponse d = new EventResponse();
        d.setId(e.id());
        d.setRestaurantId(e.restaurantId());
        d.setTitle(e.title());
        d.setDescription(e.description());
        d.setStartsAt(e.startsAt());
        d.setEndsAt(e.endsAt());
        d.setImageUrl(e.imageUrl());
        d.setStatus(e.status() != null ? e.status().name() : null);
        d.setCreatedAt(e.createdAt());
        d.setUpdatedAt(e.updatedAt());
        return d;
    }
}

