package com.minh.Online.Food.Ordering.adapters.web;

import com.minh.Online.Food.Ordering.adapters.web.dto.FoodResponse;
import com.minh.Online.Food.Ordering.adapters.web.mapper.FoodWebMapper;
import com.minh.Online.Food.Ordering.domain.ports.in.food.GetFoodUseCase;
import com.minh.Online.Food.Ordering.domain.ports.in.food.ListFoodsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/foods")
public class FoodController {

    private final GetFoodUseCase getUC;
    private final ListFoodsUseCase listUC;

    @GetMapping("/{id}")
    public ResponseEntity<FoodResponse> get(@PathVariable Long id){
        return getUC.getPublic(id)
                .map(FoodWebMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    public List<FoodResponse> search(@RequestParam(required = false) Long restaurantId,
                                     @RequestParam(required = false) Long categoryId,
                                     @RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "12") int size){
        return listUC.listPublic(restaurantId, categoryId, page, size).stream()
                .map(FoodWebMapper::toResponse).toList();
    }
}

