package com.minh.Online.Food.Ordering.modules.food.controller;

import com.minh.Online.Food.Ordering.modules.food.Food;
import com.minh.Online.Food.Ordering.modules.food.dto.CreateFoodRequest;
import com.minh.Online.Food.Ordering.modules.food.service.FoodService;
import com.minh.Online.Food.Ordering.modules.restaurant.Restaurant;
import com.minh.Online.Food.Ordering.modules.restaurant.service.RestaurantService;
import com.minh.Online.Food.Ordering.modules.user.dto.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/food")
public class AdminFoodController {

    private final FoodService foodService;
    private final RestaurantService restaurantService;

    @Autowired
    public AdminFoodController(FoodService foodService,
                               RestaurantService restaurantService) {
        this.foodService = foodService;
        this.restaurantService = restaurantService;
    }

    @PostMapping
    public ResponseEntity<?> createFood(
            @RequestBody CreateFoodRequest createFoodRequest) {
        try {
            Restaurant restaurant = restaurantService.findRestaurantById(createFoodRequest.getRestaurantId());
            if (restaurant == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new MessageResponse("Restaurant not found with id: " + createFoodRequest.getRestaurantId()));
            }

            Food food = foodService.createFood(createFoodRequest, createFoodRequest.getCategory(), restaurant);
            return new ResponseEntity<>(food, HttpStatus.CREATED);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Error creating food: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Invalid request: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteFood(
            @PathVariable Long id) {
        try {
            foodService.deleteFood(id);

            MessageResponse res = new MessageResponse();
            res.setMessage("Successfully deleted food");

            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Error deleting food: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateFoodAvailabilityStatus(
            @PathVariable Long id) {
        try {
            Food food = foodService.updateAvailibilityStatus(id);
            if (food == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new MessageResponse("Food not found with id: " + id));
            }
            return ResponseEntity.ok(food);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Error updating food availability: " + e.getMessage()));
        }
    }
}