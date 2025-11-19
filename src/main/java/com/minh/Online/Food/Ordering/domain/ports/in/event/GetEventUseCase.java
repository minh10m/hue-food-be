package com.minh.Online.Food.Ordering.domain.ports.in.event;

import com.minh.Online.Food.Ordering.domain.model.Event;

import java.util.Optional;

public interface GetEventUseCase { Optional<Event> getPublic(Long eventId); Optional<Event> getMine(Long restaurantId, Long eventId); }

