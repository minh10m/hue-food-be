package com.minh.Online.Food.Ordering.modules.food.controller;


import com.minh.Online.Food.Ordering.modules.food.Food;
import com.minh.Online.Food.Ordering.modules.food.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FoodController {

    private final FoodService foodService;
    @Autowired
    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @GetMapping("/food/search")
    public ResponseEntity<List<Food>> searchFood(
            @RequestParam String name) {
        try {
            List<Food> foods = foodService.searchFood(name);
            return ResponseEntity.ok(foods);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/restaurant/{restaurantId}/food")
    public ResponseEntity<List<Food>> getRestaurantFood(
            @RequestParam(required = false, defaultValue = "false") boolean vegetarian,
            @RequestParam(required = false, defaultValue = "false") boolean seasonal,
            @RequestParam(required = false, defaultValue = "false") boolean nonVeg,
            @RequestParam(required = false, name = "food-category") String foodCategory,
            @PathVariable Long restaurantId) {
        try {
            List<Food> foods = foodService.getRestaurantsFood(restaurantId, vegetarian, nonVeg, seasonal, foodCategory);
            return ResponseEntity.ok(foods);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


}
