package com.minh.Online.Food.Ordering.domain.model;

import java.time.Instant;
import java.util.Objects;

public final class Restaurant {
    private final Long id;
    private final Long ownerId;
    private final String name;
    private final String description;
    private final String cuisineType;
    private final String street;
    private final String city;
    private final String email;
    private final String mobile;
    private final String twitter;
    private final String instagram;
    private final String openingHours;
    private final String image;
    private final RestaurantStatus status;
    private final Instant createdAt;
    private final Instant updatedAt;

    public Restaurant(Long id, Long ownerId, String name, String description, String cuisineType,
                      String street, String city,
                      String email, String mobile, String twitter, String instagram,
                      String openingHours, String image, RestaurantStatus status,
                      Instant createdAt, Instant updatedAt) {
        this.id = id; this.ownerId = ownerId; this.name = name; this.description = description; this.cuisineType = cuisineType;
        this.street = street; this.city = city;
        this.email = email; this.mobile = mobile; this.twitter = twitter; this.instagram = instagram;
        this.openingHours = openingHours; this.image = image; this.status = status;
        this.createdAt = createdAt; this.updatedAt = updatedAt;
    }

    // getters
    public Long id() { return id; }
    public Long ownerId() { return ownerId; }
    public String name() { return name; }
    public String description() { return description; }
    public String cuisineType() { return cuisineType; }
    public String street() { return street; }
    public String city() { return city; }
    public String email() { return email; }
    public String mobile() { return mobile; }
    public String twitter() { return twitter; }
    public String instagram() { return instagram; }
    public String openingHours() { return openingHours; }
    public String image() { return image; }
    public RestaurantStatus status() { return status; }
    public Instant createdAt() { return createdAt; }
    public Instant updatedAt() { return updatedAt; }

    // helpers (copy immutables)
    public Restaurant withId(Long newId) {
        return new Restaurant(newId, ownerId, name, description, cuisineType, street, city, email, mobile, twitter, instagram, openingHours, image, status, createdAt, updatedAt);
    }
    public Restaurant withStatus(RestaurantStatus s, Instant now) {
        return new Restaurant(id, ownerId, name, description, cuisineType, street, city, email, mobile, twitter, instagram, openingHours, image, s, createdAt, now);
    }
    public Restaurant merge(Restaurant patch, Instant now) {
        return new Restaurant(
                id, ownerId,
                nv(patch.name, name), nv(patch.description, description), nv(patch.cuisineType, cuisineType),
                nv(patch.street, street), nv(patch.city, city),
                nv(patch.email, email), nv(patch.mobile, mobile), nv(patch.twitter, twitter), nv(patch.instagram, instagram),
                nv(patch.openingHours, openingHours), nv(patch.image, image),
                patch.status != null ? patch.status : status,
                createdAt, now
        );
    }
    private static <T> T nv(T a, T b){ return a != null ? a : b; }

    @Override public boolean equals(Object o){ return (o instanceof Restaurant r) && Objects.equals(id, r.id); }
    @Override public int hashCode(){ return Objects.hashCode(id); }
}
