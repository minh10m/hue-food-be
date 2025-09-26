package com.minh.Online.Food.Ordering.modules.user;

import com.minh.Online.Food.Ordering.modules.user.dto.UserProfileDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.minh.Online.Food.Ordering.modules.user.dto.UpdateUserRequest;
import com.minh.Online.Food.Ordering.modules.user.service.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User Management", description = "APIs for managing user information and authentication")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserService userService;

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

    @Operation(summary = "Get user profile", description = "Retrieves the profile of the currently authenticated user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved user profile"),
        @ApiResponse(responseCode = "401", description = "User not authenticated"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(Authentication authentication) {
        try {
            User user = getAuthenticatedUser(authentication);
            UserProfileDTO profileDTO = UserProfileDTO.fromUser(user);
            return ResponseEntity.ok(profileDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving user profile: " + e.getMessage());
        }
    }

    @Operation(summary = "Update user profile", description = "Updates the profile of the currently authenticated user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully updated user profile"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "401", description = "User not authenticated"),
        @ApiResponse(responseCode = "409", description = "Email already in use"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/profile/update")
    public ResponseEntity<?> updateUserProfile(
            Authentication authentication,
            @Valid @RequestBody UpdateUserRequest updateRequest) {
        try {
            User currentUser = getAuthenticatedUser(authentication);
            
            // Update user details
            User updatedUser = userService.updateUser(currentUser.getId(), updateRequest);
            
            // Convert to DTO to avoid sending sensitive data
            UserProfileDTO profileDTO = UserProfileDTO.fromUser(updatedUser);
            return ResponseEntity.ok(profileDTO);
            
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            if (e.getMessage() != null && e.getMessage().contains("already in use")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating profile: " + e.getMessage());
        }
    }
}
