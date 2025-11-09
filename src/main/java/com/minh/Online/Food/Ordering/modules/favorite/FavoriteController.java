package com.minh.Online.Food.Ordering.modules.favorite;

import com.minh.Online.Food.Ordering.modules.restaurant.dto.RestaurantDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/me/favorites") // separate private namespace is cleaner
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PutMapping("/{restaurantId}")
    public ResponseEntity<?> toggleFavorite(
            Authentication authentication,
            @PathVariable Long restaurantId) {

        Long userId = getUserId(authentication); // extract from principal/jwt
        RestaurantDto dto = favoriteService.toggleFavorite(restaurantId, userId);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<?> myFavorites(Authentication authentication) {
        Long userId = getUserId(authentication);
        return ResponseEntity.ok(favoriteService.getMyFavorites(userId));
    }

    private Long getUserId(Authentication auth) {
        if (auth == null || !auth.isAuthenticated()) {
            throw new org.springframework.security.access.AccessDeniedException("Unauthorized");
        }
        // If your principal is your User entity:
        Object p = auth.getPrincipal();
        if (p instanceof com.minh.Online.Food.Ordering.modules.user.User u) return u.getId();
        // Otherwise extract from a custom principal/JWT claim
        throw new IllegalStateException("Unsupported principal");
    }
}

