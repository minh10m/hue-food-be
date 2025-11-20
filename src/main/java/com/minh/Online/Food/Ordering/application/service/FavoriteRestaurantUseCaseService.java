package com.minh.Online.Food.Ordering.application.service;

import com.minh.Online.Food.Ordering.domain.model.FavoriteRestaurant;
import com.minh.Online.Food.Ordering.domain.ports.in.favorite.AddFavoriteRestaurantUseCase;
import com.minh.Online.Food.Ordering.domain.ports.in.favorite.GetUserFavoriteRestaurantsUseCase;
import com.minh.Online.Food.Ordering.domain.ports.in.favorite.RemoveFavoriteRestaurantUseCase;
import com.minh.Online.Food.Ordering.domain.ports.out.FavoriteRestaurantRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FavoriteRestaurantUseCaseService implements
        AddFavoriteRestaurantUseCase,
        RemoveFavoriteRestaurantUseCase,
        GetUserFavoriteRestaurantsUseCase {

    private final FavoriteRestaurantRepositoryPort repo;

    public FavoriteRestaurantUseCaseService(FavoriteRestaurantRepositoryPort repo) {
        this.repo = repo;
    }

    @Override
    @Transactional
    public void addFavorite(Long userId, Long restaurantId) {
        if (repo.existsByUserIdAndRestaurantId(userId, restaurantId)) {
            // tuỳ bạn: hoặc im lặng, hoặc throw
            throw new IllegalStateException("Restaurant đã được favorite");
        }
        repo.save(new FavoriteRestaurant(null, userId, restaurantId));
    }

    @Override
    @Transactional
    public void removeFavorite(Long userId, Long restaurantId) {
        repo.deleteByUserIdAndRestaurantId(userId, restaurantId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Long> getFavoriteRestaurantIds(Long userId) {
        return repo.findByUserId(userId)
                .stream()
                .map(FavoriteRestaurant::restaurantId)
                .toList();
    }
}


