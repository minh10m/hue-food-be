package com.minh.Online.Food.Ordering.modules.event.controller;

import com.minh.Online.Food.Ordering.modules.event.dto.CreateEventRequest;
import com.minh.Online.Food.Ordering.modules.event.dto.EventResponse;
import com.minh.Online.Food.Ordering.modules.event.service.EventService;
import com.minh.Online.Food.Ordering.modules.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @GetMapping("/events")
    public ResponseEntity<List<EventResponse>> getAll() {
        return ResponseEntity.ok(eventService.getPublicEvents());
    }

    @PostMapping("/admin/events/restaurant/{restaurantId}")
    public ResponseEntity<EventResponse> create(
            @PathVariable Long restaurantId,
            @Valid @RequestBody CreateEventRequest req,
            Authentication authentication
    ) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        EventResponse created = eventService.createForRestaurant(restaurantId, userId, req);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/events/{eventId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long eventId, Authentication authentication) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        eventService.deleteEvent(eventId, userId);
    }
}
