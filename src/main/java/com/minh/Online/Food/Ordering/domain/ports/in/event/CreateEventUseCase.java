package com.minh.Online.Food.Ordering.domain.ports.in.event;

import com.minh.Online.Food.Ordering.domain.model.Event;

public interface CreateEventUseCase { Event create(Event e); }
