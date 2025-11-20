package com.minh.Online.Food.Ordering.application.service;

import com.minh.Online.Food.Ordering.domain.model.Event;
import com.minh.Online.Food.Ordering.domain.model.EventStatus;
import com.minh.Online.Food.Ordering.domain.ports.in.event.*;
import com.minh.Online.Food.Ordering.domain.ports.out.EventRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class EventUseCaseService implements
        CreateEventUseCase, UpdateEventUseCase, UpdateEventStatusUseCase,
        DeleteEventUseCase, GetEventUseCase, ListEventsUseCase {

    private final EventRepositoryPort repo;

    public EventUseCaseService(EventRepositoryPort repo) { this.repo = repo; }

    @Override @Transactional
    public Event create(Event e) {
        if (e.restaurantId() == null) throw new IllegalArgumentException("restaurantId required");
        if (e.title() == null || e.title().isBlank()) throw new IllegalArgumentException("title required");
        if (e.startsAt() == null || e.endsAt() == null) throw new IllegalArgumentException("start/end required");
        if (e.endsAt().isBefore(e.startsAt())) throw new IllegalArgumentException("endsAt < startsAt");

        var now = Instant.now();
        var toSave = new Event(
                null, e.restaurantId(), e.title(), e.description(),
                e.startsAt(), e.endsAt(), e.imageUrl(),
                e.status() != null ? e.status() : EventStatus.DRAFT,
                now, now
        );
        return repo.save(toSave);
    }

    @Override @Transactional
    public Optional<Event> update(Long restaurantId, Long eventId, Event patch) {
        var now = Instant.now();
        return repo.findByIdAndRestaurantId(eventId, restaurantId)
                .map(cur -> repo.save(cur.merge(patch, now)));
    }

    @Override @Transactional
    public Event updateStatus(Long restaurantId, Long eventId, EventStatus status) {
        var now = Instant.now();
        var ev = repo.findByIdAndRestaurantId(eventId, restaurantId)
                .orElseThrow(() -> new NoSuchElementException("Event not found"));
        return repo.save(ev.withStatus(status, now));
    }

    @Override @Transactional
    public void delete(Long restaurantId, Long eventId) {
        var ev = repo.findByIdAndRestaurantId(eventId, restaurantId)
                .orElseThrow(() -> new NoSuchElementException("Event not found"));
        repo.deleteById(ev.id());
    }

    @Override public Optional<Event> getPublic(Long eventId){ return repo.findById(eventId); }
    @Override public Optional<Event> getMine(Long restaurantId, Long eventId){ return repo.findByIdAndRestaurantId(eventId, restaurantId); }

    @Override
    public List<Event> listPublicUpcoming(Instant from, int page, int size) {
        return repo.findUpcomingPublic(from != null ? from : Instant.now(), page, size);
    }

    @Override
    public List<Event> listByRestaurant(Long restaurantId) { return repo.findByRestaurant(restaurantId); }

    @Override
    public List<Event> listByRestaurantInRange(Long restaurantId, Instant from, Instant to) {
        return repo.findByRestaurantInRange(restaurantId, from, to);
    }
}

