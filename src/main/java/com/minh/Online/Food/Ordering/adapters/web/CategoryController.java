package com.minh.Online.Food.Ordering.adapters.web;

import com.minh.Online.Food.Ordering.adapters.web.dto.CategoryResponse;
import com.minh.Online.Food.Ordering.adapters.web.dto.CreateCategoryRequest;
import com.minh.Online.Food.Ordering.adapters.web.mapper.CategoryWebMapper;
import com.minh.Online.Food.Ordering.domain.ports.in.category.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {

    private final CreateCategoryUseCase createUC;
    private final UpdateCategoryUseCase updateUC;
    private final DeleteCategoryUseCase deleteUC;
    private final GetCategoryUseCase getUC;
    private final ListCategoriesUseCase listUC;

    @PostMapping
    public ResponseEntity<CategoryResponse> create(@Valid @RequestBody CreateCategoryRequest req){
        var saved = createUC.create(CategoryWebMapper.toDomain(req));
        var resp = CategoryWebMapper.toResponse(saved);
        return ResponseEntity.created(URI.create("/api/categories/" + resp.getId()))
                .body(resp);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> update(@PathVariable Long id,
                                                   @Valid @RequestBody CreateCategoryRequest req){
        return updateUC.update(req.getRestaurantId(), id, CategoryWebMapper.toPatch(req))
                .map(CategoryWebMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id, @RequestParam Long restaurantId){
        deleteUC.delete(restaurantId, id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> get(@PathVariable Long id, @RequestParam Long restaurantId){
        return getUC.get(restaurantId, id)
                .map(CategoryWebMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    public List<CategoryResponse> list(@RequestParam Long restaurantId){
        return listUC.listByRestaurant(restaurantId).stream().map(CategoryWebMapper::toResponse).toList();
    }
}
