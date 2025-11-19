package com.minh.Online.Food.Ordering.adapters.web;

import com.minh.Online.Food.Ordering.adapters.web.dto.AddFavoriteRestaurantRequest;
import com.minh.Online.Food.Ordering.adapters.web.mapper.FavoriteRestaurantWebMapper;
import com.minh.Online.Food.Ordering.domain.ports.in.favorite.AddFavoriteRestaurantUseCase;
import com.minh.Online.Food.Ordering.domain.ports.in.favorite.GetUserFavoriteRestaurantsUseCase;
import com.minh.Online.Food.Ordering.domain.ports.in.favorite.RemoveFavoriteRestaurantUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Bạn chỉnh lại path cho hợp FE: /api/user/favorites/restaurants chẳng hạn
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/favorites/restaurants")
public class FavoriteRestaurantController {

    private final AddFavoriteRestaurantUseCase addUseCase;
    private final RemoveFavoriteRestaurantUseCase removeUseCase;
    private final GetUserFavoriteRestaurantsUseCase getUseCase;

    @PostMapping
    public ResponseEntity<?> addFavorite(
            @Valid @RequestBody AddFavoriteRestaurantRequest req
            /*, @AuthenticationPrincipal CustomUserPrincipal principal */
    ) {
        Long userId = getCurrentUserId(); // TODO: lấy từ SecurityContext/JWT của bạn
        Long restaurantId = FavoriteRestaurantWebMapper.toRestaurantId(req);
        addUseCase.addFavorite(userId, restaurantId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{restaurantId}")
    public ResponseEntity<?> removeFavorite(
            @PathVariable Long restaurantId
            /*, @AuthenticationPrincipal CustomUserPrincipal principal */
    ) {
        Long userId = getCurrentUserId();
        removeUseCase.removeFavorite(userId, restaurantId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<?> getUserFavoriteRestaurants(
            /* @AuthenticationPrincipal CustomUserPrincipal principal */
    ) {
        Long userId = getCurrentUserId();
        return ResponseEntity.ok(getUseCase.getFavoriteRestaurantIds(userId));
    }

    private Long getCurrentUserId() {
        throw new UnsupportedOperationException("TODO: implement getCurrentUserId()");
    }
}

