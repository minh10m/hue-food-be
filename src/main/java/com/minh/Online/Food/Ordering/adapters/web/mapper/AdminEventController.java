package com.minh.Online.Food.Ordering.adapters.web.mapper;

import com.minh.Online.Food.Ordering.adapters.web.dto.CreateEventRequest;
import com.minh.Online.Food.Ordering.adapters.web.dto.EventResponse;
import com.minh.Online.Food.Ordering.domain.model.EventStatus;
import com.minh.Online.Food.Ordering.domain.ports.in.event.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.Instant;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/restaurants/{restaurantId}/events")
public class AdminEventController {

    private final CreateEventUseCase createUC;
    private final UpdateEventUseCase updateUC;
    private final UpdateEventStatusUseCase statusUC;
    private final DeleteEventUseCase deleteUC;
    private final GetEventUseCase getUC;
    private final ListEventsUseCase listUC;

    @PostMapping
    public ResponseEntity<EventResponse> create(@PathVariable Long restaurantId,
                                                @Valid @RequestBody CreateEventRequest req){
        var saved = createUC.create(EventWebMapper.toDomain(restaurantId, req));
        var dto = EventWebMapper.toResponse(saved);
        return ResponseEntity.created(URI.create("/api/admin/restaurants/"+restaurantId+"/events/"+dto.getId()))
                .body(dto);
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<EventResponse> update(@PathVariable Long restaurantId,
                                                @PathVariable Long eventId,
                                                @Valid @RequestBody CreateEventRequest req){
        return updateUC.update(restaurantId, eventId, EventWebMapper.toPatch(restaurantId, req))
                .map(EventWebMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PatchMapping("/{eventId}/status")
    public ResponseEntity<EventResponse> updateStatus(@PathVariable Long restaurantId,
                                                      @PathVariable Long eventId,
                                                      @RequestParam EventStatus status){
        var saved = statusUC.updateStatus(restaurantId, eventId, status);
        return ResponseEntity.ok(EventWebMapper.toResponse(saved));
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventResponse> getMine(@PathVariable Long restaurantId, @PathVariable Long eventId){
        return getUC.getMine(restaurantId, eventId)
                .map(EventWebMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    public List<EventResponse> listMine(@PathVariable Long restaurantId,
                                        @RequestParam(required = false) Long fromEpoch,
                                        @RequestParam(required = false) Long toEpoch){
        if (fromEpoch != null && toEpoch != null) {
            return listUC.listByRestaurantInRange(restaurantId, Instant.ofEpochMilli(fromEpoch), Instant.ofEpochMilli(toEpoch))
                    .stream().map(EventWebMapper::toResponse).toList();
        }
        return listUC.listByRestaurant(restaurantId).stream().map(EventWebMapper::toResponse).toList();
    }

    @DeleteMapping("/{eventId}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long restaurantId, @PathVariable Long eventId){
        deleteUC.delete(restaurantId, eventId);
    }
}

