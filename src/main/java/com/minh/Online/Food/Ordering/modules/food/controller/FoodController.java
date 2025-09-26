package com.minh.Online.Food.Ordering.modules.food.controller;

import com.minh.Online.Food.Ordering.modules.food.service.FoodService;
import com.minh.Online.Food.Ordering.modules.food.model.Food;
import com.minh.Online.Food.Ordering.modules.user.User;
import com.minh.Online.Food.Ordering.modules.restaurant.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private RestaurantService restaurantService;


    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFood(
            @RequestParam String name,
            @AuthenticationPrincipal User user) throws Exception {
            
        if (user == null) {
            throw new Exception("User not authenticated");
        }
        
        List<Food> foods = foodService.searchFood(name);
        return new ResponseEntity<>(foods, HttpStatus.OK);
    }


    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Food>> getRestaurantFood(
            @RequestParam(required = false, defaultValue = "false") boolean vegetarian,
            @RequestParam(required = false, defaultValue = "false") boolean seasonal,
            @RequestParam(required = false, defaultValue = "false") boolean nonVeg,
            @RequestParam(required = false, name = "food-category") String foodCategory,
            @PathVariable Long restaurantId,
            @AuthenticationPrincipal User user) throws Exception {
                
        if (user == null) {
            throw new Exception("User not authenticated");
        }

        List<Food> foods = foodService.getRestaurantsFood(restaurantId, vegetarian, nonVeg, seasonal, foodCategory);
        return new ResponseEntity<>(foods, HttpStatus.OK);
    }



}
