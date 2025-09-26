package com.minh.Online.Food.Ordering.modules.restaurant.controller;

import com.minh.Online.Food.Ordering.modules.restaurant.service.RestaurantService;
import com.minh.Online.Food.Ordering.modules.restaurant.dto.CreateRestaurantRequest;
import com.minh.Online.Food.Ordering.modules.restaurant.Restaurant;
import com.minh.Online.Food.Ordering.modules.user.User;
import com.minh.Online.Food.Ordering.modules.user.dto.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin/restaurants")
public class AdminRestaurantController {

    @Autowired
    private RestaurantService restaurantService;


    @PostMapping()
    public ResponseEntity<?> createRestaurant(
            @Valid @RequestBody CreateRestaurantRequest req,
            @AuthenticationPrincipal User user) {
        try {
            if (user == null) {
                return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
            }
            
            if (req == null || req.getAddress() == null) {
                return new ResponseEntity<>("Restaurant address is required", HttpStatus.BAD_REQUEST);
            }

            Restaurant restaurant = restaurantService.createRestaurant(req, user);
            return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
            
        } catch (Exception e) {
            return new ResponseEntity<>("Error creating restaurant: " + e.getMessage(), 
                                     HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRestaurant(
            @Valid @RequestBody CreateRestaurantRequest req,
            @AuthenticationPrincipal User user,
            @PathVariable Long id) {
        try {
            if (user == null) {
                return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
            }

            Restaurant restaurant = restaurantService.updateRestaurant(id, req);
            return new ResponseEntity<>(restaurant, HttpStatus.OK);
            
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating restaurant: " + e.getMessage(), 
                                     HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRestaurant(
            @AuthenticationPrincipal User user,
            @PathVariable Long id) {
        try {
            if (user == null) {
                return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
            }

            restaurantService.deleteRestaurant(id);
            MessageResponse message = new MessageResponse();
            message.setMessage("Restaurant deleted successfully");
            return new ResponseEntity<>(message, HttpStatus.OK);
            
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting restaurant: " + e.getMessage(), 
                                     HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateRestaurantStatus(
            @AuthenticationPrincipal User user,
            @PathVariable Long id) {
        try {
            if (user == null) {
                return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
            }

            Restaurant restaurant = restaurantService.updateRestaurantStatus(id);
            return new ResponseEntity<>(restaurant, HttpStatus.OK);
            
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating restaurant status: " + e.getMessage(), 
                                     HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user")
    public ResponseEntity<?> findRestaurantByUserId(
            @AuthenticationPrincipal User user) {
        try {
            if (user == null) {
                return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
            }

            Restaurant restaurant = restaurantService.getRestaurantByUserId(user.getId());
            if (restaurant == null) {
                return new ResponseEntity<>("Restaurant not found for this user", HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(restaurant, HttpStatus.OK);
            
        } catch (Exception e) {
            return new ResponseEntity<>("Error finding restaurant: " + e.getMessage(), 
                                     HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
