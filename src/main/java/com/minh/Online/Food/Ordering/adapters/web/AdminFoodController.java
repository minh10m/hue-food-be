package com.minh.Online.Food.Ordering.adapters.web;

import com.minh.Online.Food.Ordering.adapters.web.dto.CreateFoodRequest;
import com.minh.Online.Food.Ordering.adapters.web.dto.FoodResponse;
import com.minh.Online.Food.Ordering.adapters.web.mapper.FoodWebMapper;
import com.minh.Online.Food.Ordering.domain.model.FoodAvailability;
import com.minh.Online.Food.Ordering.domain.ports.in.food.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/restaurants/{restaurantId}/foods")
public class AdminFoodController {

    private final CreateFoodUseCase createUC;
    private final UpdateFoodUseCase updateUC;
    private final UpdateFoodAvailabilityUseCase availabilityUC;
    private final DeleteFoodUseCase deleteUC;
    private final GetFoodUseCase getUC;
    private final ListFoodsUseCase listUC;

    @PostMapping
    public ResponseEntity<FoodResponse> create(@PathVariable Long restaurantId,
                                               @Valid @RequestBody CreateFoodRequest req){
        var saved = createUC.create(FoodWebMapper.toDomain(restaurantId, req));
        var dto = FoodWebMapper.toResponse(saved);
        return ResponseEntity.created(URI.create("/api/admin/restaurants/"+restaurantId+"/foods/"+dto.getId())).body(dto);
    }

    @PutMapping("/{foodId}")
    public ResponseEntity<FoodResponse> update(@PathVariable Long restaurantId,
                                               @PathVariable Long foodId,
                                               @Valid @RequestBody CreateFoodRequest req){
        return updateUC.update(restaurantId, foodId, FoodWebMapper.toPatch(restaurantId, req))
                .map(FoodWebMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PatchMapping("/{foodId}/availability")
    public ResponseEntity<FoodResponse> availability(@PathVariable Long restaurantId,
                                                     @PathVariable Long foodId,
                                                     @RequestParam FoodAvailability availability){
        var saved = availabilityUC.updateAvailability(restaurantId, foodId, availability);
        return ResponseEntity.ok(FoodWebMapper.toResponse(saved));
    }

    @GetMapping("/{foodId}")
    public ResponseEntity<FoodResponse> getMine(@PathVariable Long restaurantId, @PathVariable Long foodId){
        return getUC.getMine(restaurantId, foodId)
                .map(FoodWebMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    public List<FoodResponse> listMine(@PathVariable Long restaurantId,
                                       @RequestParam(required = false) Long categoryId){
        return listUC.listPublic(restaurantId, categoryId, 0, 1000).stream() // hoặc viết riêng listByRestaurant(owner, restaurantId)
                .map(FoodWebMapper::toResponse).toList();
    }

    @DeleteMapping("/{foodId}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long restaurantId, @PathVariable Long foodId){
        deleteUC.delete(restaurantId, foodId);
    }
}
