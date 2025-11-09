package com.minh.Online.Food.Ordering.modules.favorite;

import com.minh.Online.Food.Ordering.modules.restaurant.Restaurant;
import com.minh.Online.Food.Ordering.modules.restaurant.RestaurantRepository;
import com.minh.Online.Food.Ordering.modules.restaurant.dto.RestaurantDto;
import com.minh.Online.Food.Ordering.modules.restaurant.dto.RestaurantMapper;
import com.minh.Online.Food.Ordering.modules.user.User;
import com.minh.Online.Food.Ordering.modules.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final UserRepository userRepo;
    private final RestaurantRepository restaurantRepo;
    private final RestaurantMapper restaurantMapper;

    @Transactional
    public RestaurantDto toggleFavorite(Long restaurantId, Long userId) {
        User user = userRepo.findByIdWithFavorites(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Restaurant restaurant = restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));

        if (user.getFavorites().contains(restaurant)) {
            user.getFavorites().remove(restaurant);
        } else {
            user.getFavorites().add(restaurant);
        }
        // No explicit save required; JPA flushes at commit.

        return restaurantMapper.toDto(restaurant);
    }

    @Transactional(readOnly = true)
    public List<RestaurantDto> getMyFavorites(Long userId) {
        User user = userRepo.findByIdWithFavorites(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return user.getFavorites().stream().map(restaurantMapper::toDto).toList();
    }
}
