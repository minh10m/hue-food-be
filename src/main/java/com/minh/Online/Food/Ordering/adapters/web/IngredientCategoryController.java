package com.minh.Online.Food.Ordering.adapters.web;

import com.minh.Online.Food.Ordering.adapters.web.dto.IngredientCategoryRequest;
import com.minh.Online.Food.Ordering.adapters.web.dto.IngredientCategoryResponse;
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
@RequestMapping("/api/admin/restaurants/{restaurantId}/ingredient-categories")
public class IngredientCategoryController {

    private final CreateIngredientCategoryUseCase createUC;
    private final UpdateIngredientCategoryUseCase updateUC;
    private final DeleteIngredientCategoryUseCase deleteUC;
    private final ListIngredinetCategoriesUseCase listUC;
    private final GetIngredientCategoryUseCase getUC;

    @PostMapping
    public ResponseEntity<IngredientCategoryResponse> create(@PathVariable Long restaurantId,
                                                             @Valid @RequestBody IngredientCategoryRequest req){
        var saved = createUC.create(IngredientWebMapper.toDomain(restaurantId, req));
        var dto = IngredientWebMapper.toResponse(saved);
        return ResponseEntity.created(URI.create("/api/admin/restaurants/"+restaurantId+"/ingredient-categories/"+dto.getId()))
                .body(dto);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<IngredientCategoryResponse> update(@PathVariable Long restaurantId,
                                                             @PathVariable Long categoryId,
                                                             @Valid @RequestBody IngredientCategoryRequest req){
        return updateUC.update(restaurantId, categoryId, IngredientWebMapper.toPatch(restaurantId, req))
                .map(IngredientWebMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    public List<IngredientCategoryResponse> list(@PathVariable Long restaurantId){
        return listUC.listByRestaurant(restaurantId).stream().map(IngredientWebMapper::toResponse).toList();
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<IngredientCategoryResponse> get(@PathVariable Long restaurantId, @PathVariable Long categoryId){
        return getUC.get(restaurantId, categoryId)
                .map(IngredientWebMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{categoryId}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long restaurantId, @PathVariable Long categoryId){
        deleteUC.delete(restaurantId, categoryId);
    }
}
