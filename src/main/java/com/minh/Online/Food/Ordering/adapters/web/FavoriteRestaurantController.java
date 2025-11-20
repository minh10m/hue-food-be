package com.minh.Online.Food.Ordering.adapters.web;

import com.minh.Online.Food.Ordering.adapters.security.AuthUtils;
import com.minh.Online.Food.Ordering.adapters.web.dto.AddFavoriteRestaurantRequest;
import com.minh.Online.Food.Ordering.adapters.web.mapper.FavoriteRestaurantWebMapper;
import com.minh.Online.Food.Ordering.domain.ports.in.favorite.AddFavoriteRestaurantUseCase;
import com.minh.Online.Food.Ordering.domain.ports.in.favorite.GetUserFavoriteRestaurantsUseCase;
import com.minh.Online.Food.Ordering.domain.ports.in.favorite.RemoveFavoriteRestaurantUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

// Bạn chỉnh lại path cho hợp FE: /api/user/favorites/restaurants chẳng hạn
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/favorites/restaurants")
public class FavoriteRestaurantController {

    private final AddFavoriteRestaurantUseCase addUseCase;
    private final RemoveFavoriteRestaurantUseCase removeUseCase;
    private final GetUserFavoriteRestaurantsUseCase getUseCase;

    private final AuthUtils auth;

    @PostMapping
    public ResponseEntity<?> addFavorite(
            Authentication authentication,
            @Valid @RequestBody AddFavoriteRestaurantRequest req
    ) {
        Long userId = auth.currentUserId(authentication);
        Long restaurantId = FavoriteRestaurantWebMapper.toRestaurantId(req);
        addUseCase.addFavorite(userId, restaurantId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{restaurantId}")
    public ResponseEntity<?> removeFavorite(
            Authentication authentication,
            @PathVariable Long restaurantId
    ) {
        Long userId = auth.currentUserId(authentication);
        removeUseCase.removeFavorite(userId, restaurantId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<?> getUserFavoriteRestaurants(
            Authentication authentication
    ) {
        Long userId = auth.currentUserId(authentication);
        return ResponseEntity.ok(getUseCase.getFavoriteRestaurantIds(userId));
    }
}

