package com.minh.Online.Food.Ordering.modules.food.controller;

import com.minh.Online.Food.Ordering.modules.food.dto.CreateFoodRequest;
import com.minh.Online.Food.Ordering.modules.food.service.FoodService;
import com.minh.Online.Food.Ordering.modules.food.model.Food;
import com.minh.Online.Food.Ordering.modules.restaurant.Restaurant;
import com.minh.Online.Food.Ordering.modules.user.User;
import com.minh.Online.Food.Ordering.modules.user.dto.MessageResponse;
import com.minh.Online.Food.Ordering.modules.restaurant.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/food")
public class AdminFoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private RestaurantService restaurantService;


    @PostMapping
    public ResponseEntity<Food> createFood(
            @RequestBody CreateFoodRequest createFoodRequest,
            @AuthenticationPrincipal User user) throws Exception {
                
        if (user == null) {
            throw new Exception("User not authenticated");
        }
        
        Restaurant restaurant = restaurantService.findRestaurantById(createFoodRequest.getRestaurantId());
        if (restaurant == null) {
            throw new Exception("Restaurant not found");
        }
        
        Food food = foodService.createFood(createFoodRequest, createFoodRequest.getCategory(), restaurant);
        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteFood(
            @PathVariable Long id,
            @AuthenticationPrincipal User user) throws Exception {
                
        if (user == null) {
            throw new Exception("User not authenticated");
        }

        foodService.deleteFood(id);

        MessageResponse res = new MessageResponse();
        res.setMessage("Successfully deleted food");

        return new ResponseEntity<>(res, HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Food> updateFoodAvailabilityStatus(
            @PathVariable Long id,
            @AuthenticationPrincipal User user) throws Exception {
                
        if (user == null) {
            throw new Exception("User not authenticated");
        }

        Food food = foodService.updateAvailibilityStatus(id);
        if (food == null) {
            throw new Exception("Food not found");
        }

        return new ResponseEntity<>(food, HttpStatus.OK);
    }



}
