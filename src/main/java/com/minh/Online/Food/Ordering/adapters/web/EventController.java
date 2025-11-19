package com.minh.Online.Food.Ordering.adapters.web;

import com.minh.Online.Food.Ordering.adapters.web.dto.EventResponse;
import com.minh.Online.Food.Ordering.adapters.web.mapper.EventWebMapper;
import com.minh.Online.Food.Ordering.domain.ports.in.event.GetEventUseCase;
import com.minh.Online.Food.Ordering.domain.ports.in.event.ListEventsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/events")
public class EventController {

    private final GetEventUseCase getUC;
    private final ListEventsUseCase listUC;

    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> get(@PathVariable Long id){
        return getUC.getPublic(id)
                .map(EventWebMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/upcoming")
    public List<EventResponse> upcoming(@RequestParam(required = false) Long fromEpoch,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "12") int size){
        Instant from = (fromEpoch != null) ? Instant.ofEpochMilli(fromEpoch) : Instant.now();
        return listUC.listPublicUpcoming(from, page, size).stream()
                .map(EventWebMapper::toResponse).toList();
    }
}

