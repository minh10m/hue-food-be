package com.minh.Online.Food.Ordering.adapters.web.mapper;


import com.minh.Online.Food.Ordering.domain.model.Restaurant;
import com.minh.Online.Food.Ordering.adapters.web.dto.CreateRestaurantRequest;
import com.minh.Online.Food.Ordering.adapters.web.dto.MyRestaurantResponseDTO;
import com.minh.Online.Food.Ordering.adapters.web.dto.RestaurantResponse;

import java.time.Instant;

public final class RestaurantWebMapper {
    private RestaurantWebMapper(){}

    public static Restaurant toDomain(Long ownerId, CreateRestaurantRequest req) {
        return new Restaurant(
                null,
                ownerId,
                req.getName(),
                req.getDescription(),
                req.getCuisineType(),
                req.getStreet(),
                req.getCity(),
                req.getEmail(),
                req.getMobile(),
                req.getTwitter(),
                req.getInstagram(),
                req.getOpeningHours(),
                req.getImage(),
                null, // status để service set mặc định
                Instant.now(), Instant.now()
        );
    }

    public static Restaurant toPatch(Long ownerId, CreateRestaurantRequest req){
        // dùng chung DTO cho update; cho phép null field
        return new Restaurant(
                null, ownerId,
                req.getName(), req.getDescription(), req.getCuisineType(),
                req.getStreet(), req.getCity(),
                req.getEmail(), req.getMobile(), req.getTwitter(), req.getInstagram(),
                req.getOpeningHours(), req.getImage(),
                null, null, null
        );
    }

    public static RestaurantResponse toPublic(Restaurant r){
        RestaurantResponse d = new RestaurantResponse();
        d.setId(r.id());
        d.setName(r.name());
        d.setDescription(r.description());
        d.setCuisineType(r.cuisineType());
        d.setStreet(r.street());
        d.setCity(r.city());
        d.setOpeningHours(r.openingHours());
        d.setImage(r.image());
        d.setStatus(r.status().name());
        return d;
    }

    public static MyRestaurantResponseDTO toMine(Restaurant r){
        MyRestaurantResponseDTO d = new MyRestaurantResponseDTO();
        d.setId(r.id());
        d.setOwnerId(r.ownerId());
        d.setName(r.name());
        d.setDescription(r.description());
        d.setCuisineType(r.cuisineType());
        d.setStreet(r.street());
        d.setCity(r.city());
        d.setEmail(r.email());
        d.setMobile(r.mobile());
        d.setTwitter(r.twitter());
        d.setInstagram(r.instagram());
        d.setOpeningHours(r.openingHours());
        d.setImage(r.image());
        d.setStatus(r.status().name());
        d.setCreatedAt(r.createdAt());
        d.setUpdatedAt(r.updatedAt());
        return d;
    }
}

