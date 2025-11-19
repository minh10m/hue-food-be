package com.minh.Online.Food.Ordering.domain.model;

import java.time.Instant;
import java.util.Objects;

public final class Event {
    private final Long id;
    private final Long restaurantId;   // chủ sự kiện thuộc nhà hàng nào
    private final String title;
    private final String description;
    private final Instant startsAt;
    private final Instant endsAt;
    private final String imageUrl;
    private final EventStatus status;
    private final Instant createdAt;
    private final Instant updatedAt;

    public Event(Long id, Long restaurantId, String title, String description,
                 Instant startsAt, Instant endsAt, String imageUrl,
                 EventStatus status, Instant createdAt, Instant updatedAt) {
        this.id = id; this.restaurantId = restaurantId; this.title = title; this.description = description;
        this.startsAt = startsAt; this.endsAt = endsAt; this.imageUrl = imageUrl;
        this.status = status; this.createdAt = createdAt; this.updatedAt = updatedAt;
    }

    public Long id() { return id; }
    public Long restaurantId() { return restaurantId; }
    public String title() { return title; }
    public String description() { return description; }
    public Instant startsAt() { return startsAt; }
    public Instant endsAt() { return endsAt; }
    public String imageUrl() { return imageUrl; }
    public EventStatus status() { return status; }
    public Instant createdAt() { return createdAt; }
    public Instant updatedAt() { return updatedAt; }

    public Event withId(Long newId){ return new Event(newId, restaurantId, title, description, startsAt, endsAt, imageUrl, status, createdAt, updatedAt); }
    public Event withStatus(EventStatus s, Instant now){ return new Event(id, restaurantId, title, description, startsAt, endsAt, imageUrl, s, createdAt, now); }

    public Event merge(Event patch, Instant now){
        return new Event(
                id, restaurantId,
                patch.title != null ? patch.title : title,
                patch.description != null ? patch.description : description,
                patch.startsAt != null ? patch.startsAt : startsAt,
                patch.endsAt != null ? patch.endsAt : endsAt,
                patch.imageUrl != null ? patch.imageUrl : imageUrl,
                patch.status != null ? patch.status : status,
                createdAt, now
        );
    }

    @Override public boolean equals(Object o){ return (o instanceof Event e) && Objects.equals(id, e.id); }
    @Override public int hashCode(){ return Objects.hashCode(id); }
}

