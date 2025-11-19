package com.minh.Online.Food.Ordering.adapters.web;

import com.minh.Online.Food.Ordering.adapters.web.dto.IngredientRequest;
import com.minh.Online.Food.Ordering.adapters.web.dto.IngredientResponse;
import com.minh.Online.Food.Ordering.adapters.web.mapper.IngredientWebMapper;
import com.minh.Online.Food.Ordering.domain.ports.in.ingredient.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/restaurants/{restaurantId}/ingredients")
public class IngredientController {

    private final CreateItemUseCase createUC;
    private final UpdateItemUseCase updateUC;
    private final DeleteItemUseCase deleteUC;
    private final ListItemsUseCase listUC;
    private final GetItemUseCase getUC;

    @PostMapping
    public ResponseEntity<IngredientResponse> create(@PathVariable Long restaurantId,
                                                     @Valid @RequestBody IngredientRequest req){
        var saved = createUC.create(IngredientWebMapper.toDomain(restaurantId, req));
        var dto = IngredientWebMapper.toResponse(saved);
        return ResponseEntity.created(URI.create("/api/admin/restaurants/"+restaurantId+"/ingredients/"+dto.getId()))
                .body(dto);
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<IngredientResponse> update(@PathVariable Long restaurantId,
                                                     @PathVariable Long itemId,
                                                     @Valid @RequestBody IngredientRequest req){
        return updateUC.update(restaurantId, itemId, IngredientWebMapper.toPatch(restaurantId, req))
                .map(IngredientWebMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    public List<IngredientResponse> listByRestaurant(@PathVariable Long restaurantId,
                                                     @RequestParam(required = false) Long categoryId){
        var list = (categoryId == null)
                ? listUC.listByRestaurant(restaurantId)
                : listUC.listByCategory(restaurantId, categoryId);
        return list.stream().map(IngredientWebMapper::toResponse).toList();
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<IngredientResponse> get(@PathVariable Long restaurantId, @PathVariable Long itemId){
        return getUC.get(restaurantId, itemId)
                .map(IngredientWebMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{itemId}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long restaurantId, @PathVariable Long itemId){
        deleteUC.delete(restaurantId, itemId);
    }
}
