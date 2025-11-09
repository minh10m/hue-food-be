package com.minh.Online.Food.Ordering.modules.restaurant.controller;

import com.minh.Online.Food.Ordering.modules.restaurant.dto.RestaurantResponse;
import com.minh.Online.Food.Ordering.modules.restaurant.service.RestaurantService;
import com.minh.Online.Food.Ordering.modules.restaurant.dto.RestaurantDto;
import com.minh.Online.Food.Ordering.modules.restaurant.Restaurant;
import com.minh.Online.Food.Ordering.modules.user.User;
import com.minh.Online.Food.Ordering.modules.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.minh.Online.Food.Ordering.modules.user.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/search")
    public ResponseEntity<List<Restaurant>> searchRestaurant(
            @RequestParam String keyword) {
        List<Restaurant> restaurants = restaurantService.searchRestaurant(keyword);
        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<RestaurantResponse>> getAllRestaurant() {
        return ResponseEntity.ok(restaurantService.getAllRestaurantsDto());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findRestaurantById(
            @PathVariable Long id) {
        try {
            Restaurant restaurant = restaurantService.findRestaurantById(id);
            return new ResponseEntity<>(restaurant, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    private User getAuthenticatedUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }
        
        if (authentication.getPrincipal() instanceof User) {
            return (User) authentication.getPrincipal();
        }
        
        // Fallback to database lookup if principal is not User
        String username = authentication.getName();
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + username));
    }

    @PutMapping("/{id}/add-favorites")
    public ResponseEntity<?> addToFavorites(Authentication authentication, @PathVariable Long id) {
        try {
            User user = getAuthenticatedUser(authentication);
            RestaurantDto restaurant = restaurantService.addToFavorites(id, user.getId());
            return ResponseEntity.ok(restaurant);
        } catch (org.springframework.security.access.AccessDeniedException | org.springframework.security.core.AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error adding to favorites: " + e.getMessage());
        }
    }

}
