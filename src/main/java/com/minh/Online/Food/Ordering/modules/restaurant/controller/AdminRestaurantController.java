package com.minh.Online.Food.Ordering.modules.restaurant.controller;

import com.minh.Online.Food.Ordering.modules.restaurant.service.RestaurantService;
import com.minh.Online.Food.Ordering.modules.restaurant.dto.CreateRestaurantRequest;
import com.minh.Online.Food.Ordering.modules.restaurant.Restaurant;
import com.minh.Online.Food.Ordering.modules.user.User;
import com.minh.Online.Food.Ordering.modules.user.UserRepository;
import com.minh.Online.Food.Ordering.modules.user.dto.MessageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin/restaurants")
public class AdminRestaurantController {

    @Autowired
    private RestaurantService restaurantService;
    
    @Autowired
    private UserRepository userRepository;
    
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


    @Operation(summary = "Create restaurant", description = "Creates a new restaurant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Successfully created restaurant"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "401", description = "User not authenticated"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping()
    public ResponseEntity<?> createRestaurant(
            @Valid @RequestBody CreateRestaurantRequest req,
            Authentication authentication) {
        try {
            User user = getAuthenticatedUser(authentication);
            
            if (req == null || req.getAddress() == null) {
                return ResponseEntity.badRequest().body("Restaurant address is required");
            }

            Restaurant restaurant = restaurantService.createRestaurant(req, user);
            return ResponseEntity.status(HttpStatus.CREATED).body(restaurant);
            
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating restaurant: " + e.getMessage());
        }
    }

    @Operation(summary = "Update restaurant", description = "Updates an existing restaurant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully updated restaurant"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "401", description = "User not authenticated"),
        @ApiResponse(responseCode = "404", description = "Restaurant not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRestaurant(
            @Valid @RequestBody CreateRestaurantRequest req,
            Authentication authentication,
            @PathVariable Long id) {
        try {
            getAuthenticatedUser(authentication);
            Restaurant restaurant = restaurantService.updateRestaurant(id, req);
            return ResponseEntity.ok(restaurant);
            
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating restaurant: " + e.getMessage());
        }
    }

    @Operation(summary = "Delete restaurant", description = "Deletes an existing restaurant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully deleted restaurant"),
        @ApiResponse(responseCode = "401", description = "User not authenticated"),
        @ApiResponse(responseCode = "404", description = "Restaurant not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRestaurant(
            Authentication authentication,
            @PathVariable Long id) {
        try {
            getAuthenticatedUser(authentication);
            restaurantService.deleteRestaurant(id);
            
            MessageResponse message = new MessageResponse();
            message.setMessage("Restaurant deleted successfully");
            return ResponseEntity.ok(message);
            
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting restaurant: " + e.getMessage());
        }
    }

    @Operation(summary = "Update restaurant status", description = "Toggles the active status of a restaurant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully updated restaurant status"),
        @ApiResponse(responseCode = "401", description = "User not authenticated"),
        @ApiResponse(responseCode = "404", description = "Restaurant not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateRestaurantStatus(
            Authentication authentication,
            @PathVariable Long id) {
        try {
            getAuthenticatedUser(authentication);
            Restaurant restaurant = restaurantService.updateRestaurantStatus(id);
            return ResponseEntity.ok(restaurant);
            
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating restaurant status: " + e.getMessage());
        }
    }

    @Operation(summary = "Find restaurant by ID", description = "Retrieves a restaurant by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved restaurant"),
        @ApiResponse(responseCode = "401", description = "User not authenticated"),
        @ApiResponse(responseCode = "404", description = "Restaurant not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/restaurant/{id}")
    public ResponseEntity<?> findRestaurantById(
            Authentication authentication,
            @PathVariable Long id) {
        try {
            getAuthenticatedUser(authentication);
            Restaurant restaurant = restaurantService.findRestaurantById(id);
            return ResponseEntity.ok(restaurant);
            
        } catch (Exception e) {
            if (e.getMessage().contains("not found") || e.getMessage().contains("Restaurant")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error finding restaurant: " + e.getMessage());
        }
    }
}
