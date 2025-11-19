package com.minh.Online.Food.Ordering.adapters.web;

import com.minh.Online.Food.Ordering.adapters.web.dto.CreateRestaurantRequest;
import com.minh.Online.Food.Ordering.adapters.web.dto.MyRestaurantResponseDTO;
import com.minh.Online.Food.Ordering.adapters.web.mapper.RestaurantWebMapper;
import com.minh.Online.Food.Ordering.domain.model.Restaurant;
import com.minh.Online.Food.Ordering.domain.ports.in.restaurant.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/restaurants")
public class AdminRestaurantController {

    private final CreateRestaurantUseCase createUC;
    private final UpdateRestaurantUseCase updateUC;
    private final UpdateRestaurantStatusUseCase statusUC;
    private final DeleteRestaurantUseCase deleteUC;
    private final GetRestaurantUseCase getUC;
    private final ListRestaurantsUseCase listUC;

    // Lấy ownerId từ security (JWT) là chuẩn; demo tạm lấy từ param cho dễ test
    @PostMapping
    public ResponseEntity<MyRestaurantResponseDTO> create(@RequestParam Long ownerId,
                                                          @Valid @RequestBody CreateRestaurantRequest req){
        var saved = createUC.create(RestaurantWebMapper.toDomain(ownerId, req));
        var dto = RestaurantWebMapper.toMine(saved);
        return ResponseEntity.created(URI.create("/api/admin/restaurants/" + dto.getId())).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MyRestaurantResponseDTO> update(@RequestParam Long ownerId,
                                                          @PathVariable Long id,
                                                          @Valid @RequestBody CreateRestaurantRequest req){
        return updateUC.update(ownerId, id, RestaurantWebMapper.toPatch(ownerId, req))
                .map(RestaurantWebMapper::toMine)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<MyRestaurantResponseDTO> updateStatus(@RequestParam Long ownerId,
                                                                @PathVariable Long id,
                                                                @RequestParam Restaurant.Status status){
        var saved = statusUC.updateStatus(ownerId, id, status);
        return ResponseEntity.ok(RestaurantWebMapper.toMine(saved));
    }

    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestParam Long ownerId, @PathVariable Long id){ deleteUC.delete(ownerId, id); }

    @GetMapping("/{id}")
    public ResponseEntity<MyRestaurantResponseDTO> getMine(@RequestParam Long ownerId, @PathVariable Long id){
        return getUC.getMine(ownerId, id).map(RestaurantWebMapper::toMine)
                .map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/me")
    public List<MyRestaurantResponseDTO> listMine(@RequestParam Long ownerId){
        return listUC.listMine(ownerId).stream().map(RestaurantWebMapper::toMine).toList();
    }
}

