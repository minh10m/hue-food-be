package com.minh.Online.Food.Ordering.adapters.persistence.favorite;

import com.minh.Online.Food.Ordering.domain.model.FavoriteRestaurant;
import com.minh.Online.Food.Ordering.domain.ports.out.FavoriteRestaurantRepositoryPort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class FavoriteRestaurantRepositoryAdapter implements FavoriteRestaurantRepositoryPort {

    private final SpringDataFavoriteRestaurantRepository repo;

    public FavoriteRestaurantRepositoryAdapter(SpringDataFavoriteRestaurantRepository repo) {
        this.repo = repo;
    }

    @Override
    public boolean existsByUserIdAndRestaurantId(Long userId, Long restaurantId) {
        return repo.existsByUserIdAndRestaurantId(userId, restaurantId);
    }

    @Override
    @Transactional
    public FavoriteRestaurant save(FavoriteRestaurant favorite) {
        FavoriteRestaurantJpaEntity entity = FavoriteRestaurantJpaEntity.builder()
                .id(favorite.id())
                .userId(favorite.userId())
                .restaurantId(favorite.restaurantId())
                .build();
        FavoriteRestaurantJpaEntity saved = repo.save(entity);
        return new FavoriteRestaurant(saved.getId(), saved.getUserId(), saved.getRestaurantId());
    }

    @Override
    @Transactional
    public void deleteByUserIdAndRestaurantId(Long userId, Long restaurantId) {
        repo.deleteByUserIdAndRestaurantId(userId, restaurantId);
    }

    @Override
    public List<FavoriteRestaurant> findByUserId(Long userId) {
        return repo.findByUserId(userId).stream()
                .map(e -> new FavoriteRestaurant(e.getId(), e.getUserId(), e.getRestaurantId()))
                .toList();
    }
}
