package com.minh.Online.Food.Ordering.adapters.persistence.event;

import com.minh.Online.Food.Ordering.domain.model.Event;
import com.minh.Online.Food.Ordering.domain.model.EventStatus;
import com.minh.Online.Food.Ordering.domain.ports.out.EventRepositoryPort;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Component
public class EventRepositoryAdapter implements EventRepositoryPort {

    private final SpringDataEventRepository repo;

    public EventRepositoryAdapter(SpringDataEventRepository repo) { this.repo = repo; }

    @Override @Transactional
    public Event save(Event e) {
        var entity = toEntity(e);
        var saved = repo.save(entity);
        return toDomain(saved);
    }

    @Override public Optional<Event> findById(Long id) { return repo.findById(id).map(this::toDomain); }

    @Override
    public Optional<Event> findByIdAndRestaurantId(Long id, Long restaurantId) {
        return repo.findByIdAndRestaurantId(id, restaurantId).map(this::toDomain);
    }

    @Override
    public List<Event> findUpcomingPublic(Instant from, int page, int size) {
        return repo.findUpcomingPublic(EventStatus.PUBLISHED, from, PageRequest.of(page, size))
                .map(this::toDomain).getContent();
    }

    @Override
    public List<Event> findByRestaurant(Long restaurantId) {
        return repo.findByRestaurantIdOrderByStartsAtDesc(restaurantId).stream().map(this::toDomain).toList();
    }

    @Override
    public List<Event> findByRestaurantInRange(Long restaurantId, Instant from, Instant to) {
        return repo.findByRestaurantInRange(restaurantId, from, to).stream().map(this::toDomain).toList();
    }

    @Override @Transactional
    public void deleteById(Long id) { repo.deleteById(id); }

    private Event toDomain(EventJpaEntity e){
        return new Event(
                e.getId(), e.getRestaurantId(), e.getTitle(), e.getDescription(),
                e.getStartsAt(), e.getEndsAt(), e.getImageUrl(),
                e.getStatus(), e.getCreatedAt(), e.getUpdatedAt()
        );
    }
    private EventJpaEntity toEntity(Event e){
        return EventJpaEntity.builder()
                .id(e.id())
                .restaurantId(e.restaurantId())
                .title(e.title())
                .description(e.description())
                .startsAt(e.startsAt())
                .endsAt(e.endsAt())
                .imageUrl(e.imageUrl())
                .status(e.status())
                .createdAt(e.createdAt())
                .updatedAt(e.updatedAt())
                .build();
    }
}

