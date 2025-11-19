package com.minh.Online.Food.Ordering.domain.ports.in.restaurant;

import com.minh.Online.Food.Ordering.domain.model.Restaurant;

import java.util.List;

public interface ListRestaurantsUseCase {
    List<Restaurant> listPublic(String city, String cuisine, int page, int size);
    List<Restaurant> listMine(Long ownerId);
}
