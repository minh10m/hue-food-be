package com.minh.Online.Food.Ordering.adapters.persistence.restaurant;

import com.minh.Online.Food.Ordering.adapters.persistence.user.UserJpaEntity;
import com.minh.Online.Food.Ordering.domain.model.Restaurant;
import com.minh.Online.Food.Ordering.domain.model.RestaurantStatus;
import com.minh.Online.Food.Ordering.domain.ports.out.RestaurantRepositoryPort;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class RestaurantRepositoryAdapter implements RestaurantRepositoryPort {

    private final SpringDataRestaurantRepository repo;

    public RestaurantRepositoryAdapter(SpringDataRestaurantRepository repo) { this.repo = repo; }

    @Override @Transactional
    public Restaurant save(Restaurant r) {
        var e = toEntity(r);
        // attach owner reference
        if (r.ownerId() != null && (e.getOwner() == null || e.getOwner().getId() == null)) {
            e.setOwner(UserJpaEntity.builder().id(r.ownerId()).build());
        }
        var s = repo.save(e);
        return toDomain(s);
    }

    @Override public Optional<Restaurant> findById(Long id) { return repo.findById(id).map(this::toDomain); }

    @Override public Optional<Restaurant> findByIdAndOwnerId(Long id, Long ownerId) {
        return repo.findByIdAndOwnerId(id, ownerId).map(this::toDomain);
    }

    @Override
    public List<Restaurant> findPublic(String city, String cuisine, int page, int size) {
        return repo.searchPublic(nz(city), nz(cuisine), PageRequest.of(page, size))
                .map(this::toDomain).getContent();
    }

    @Override
    public List<Restaurant> findByOwner(Long ownerId) {
        return repo.findByOwnerId(ownerId).stream().map(this::toDomain).toList();
    }

    @Override @Transactional
    public void deleteById(Long id) { repo.deleteById(id); }

    // mapping
    private Restaurant toDomain(RestaurantJpaEntity e) {
        return new Restaurant(
                e.getId(),
                e.getOwner() != null ? e.getOwner().getId() : null,
                e.getName(), e.getDescription(), e.getCuisineType(),
                e.getStreet(), e.getCity(),
                e.getEmail(), e.getMobile(), e.getTwitter(), e.getInstagram(),
                e.getOpeningHours(), e.getImage(),
                e.getStatus(),
                e.getCreatedAt(), e.getUpdatedAt()
        );
    }

    private RestaurantJpaEntity toEntity(Restaurant r) {
        return RestaurantJpaEntity.builder()
                .id(r.id())
                .owner(r.ownerId() == null ? null : UserJpaEntity.builder().id(r.ownerId()).build())
                .name(r.name()).description(r.description()).cuisineType(r.cuisineType())
                .street(r.street()).city(r.city())
                .email(r.email()).mobile(r.mobile()).twitter(r.twitter()).instagram(r.instagram())
                .openingHours(r.openingHours()).image(r.image())
                .status(r.status())
                .createdAt(r.createdAt()).updatedAt(r.updatedAt())
                .build();
    }

    private static String nz(String s){ return (s == null || s.isBlank()) ? null : s; }
}

