package com.minh.Online.Food.Ordering.domain.ports.in.event;

import com.minh.Online.Food.Ordering.domain.model.Event;
import com.minh.Online.Food.Ordering.domain.model.EventStatus;

public interface UpdateEventStatusUseCase { Event updateStatus(Long restaurantId, Long eventId, EventStatus status); }
