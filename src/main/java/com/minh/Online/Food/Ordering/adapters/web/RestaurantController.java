package com.minh.Online.Food.Ordering.adapters.web;

import com.minh.Online.Food.Ordering.adapters.web.dto.RestaurantResponse;
import com.minh.Online.Food.Ordering.adapters.web.mapper.RestaurantWebMapper;
import com.minh.Online.Food.Ordering.domain.ports.in.restaurant.GetRestaurantUseCase;
import com.minh.Online.Food.Ordering.domain.ports.in.restaurant.ListRestaurantsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/restaurants")
public class RestaurantController {

    private final GetRestaurantUseCase getUC;
    private final ListRestaurantsUseCase listUC;

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponse> get(@PathVariable Long id){
        return getUC.getPublic(id)
                .map(RestaurantWebMapper::toPublic)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    public List<RestaurantResponse> search(@RequestParam(required = false) String city,
                                           @RequestParam(required = false) String cuisine,
                                           @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "12") int size){
        return listUC.listPublic(city, cuisine, page, size)
                .stream().map(RestaurantWebMapper::toPublic).toList();
    }
}

