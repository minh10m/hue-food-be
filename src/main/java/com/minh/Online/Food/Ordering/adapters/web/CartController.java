package com.minh.Online.Food.Ordering.adapters.web;

import com.minh.Online.Food.Ordering.adapters.web.dto.AddCartItemRequest;
import com.minh.Online.Food.Ordering.adapters.web.dto.CartResponse;
import com.minh.Online.Food.Ordering.adapters.web.dto.UpdateCartItemRequest;
import com.minh.Online.Food.Ordering.adapters.web.mapper.CartWebMapper;
import com.minh.Online.Food.Ordering.domain.ports.in.cart.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {

    private final AddItemToCartUseCase addUC;
    private final UpdateCartItemUseCase updateUC;
    private final RemoveCartItemUseCase removeUC;
    private final ClearCartUseCase clearUC;
    private final GetMyCartUseCase getUC;

    @GetMapping
    public CartResponse get(@RequestParam Long userId){
        return CartWebMapper.toResponse(getUC.getOrCreate(userId));
    }

    @PostMapping("/items")
    public CartResponse addItem(@RequestParam Long userId, @Valid @RequestBody AddCartItemRequest req){
        var cart = addUC.add(userId, req.getFoodId(), req.getFoodName(), req.getUnitPrice(), req.getQuantity(), req.getRestaurantId());
        return CartWebMapper.toResponse(cart);
    }

    @PutMapping("/items/{itemId}")
    public CartResponse updateQty(@RequestParam Long userId, @PathVariable Long itemId, @Valid @RequestBody UpdateCartItemRequest req){
        var cart = updateUC.updateQuantity(userId, itemId, req.getQuantity());
        return CartWebMapper.toResponse(cart);
    }

    @DeleteMapping("/items/{itemId}")
    public CartResponse remove(@RequestParam Long userId, @PathVariable Long itemId){
        var cart = removeUC.remove(userId, itemId);
        return CartWebMapper.toResponse(cart);
    }

    @DeleteMapping("/items")
    public CartResponse clear(@RequestParam Long userId){
        var cart = clearUC.clear(userId);
        return CartWebMapper.toResponse(cart);
    }
}
