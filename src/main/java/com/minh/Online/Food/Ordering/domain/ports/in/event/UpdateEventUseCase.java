package com.minh.Online.Food.Ordering.domain.ports.in.event;

import com.minh.Online.Food.Ordering.domain.model.Event;

import java.util.Optional;

public interface UpdateEventUseCase { Optional<Event> update(Long restaurantId, Long eventId, Event patch); }

