package com.minh.Online.Food.Ordering.adapters.web;

import com.minh.Online.Food.Ordering.adapters.web.dto.OrderResponse;
import com.minh.Online.Food.Ordering.adapters.web.mapper.OrderWebMapper;
import com.minh.Online.Food.Ordering.domain.model.OrderStatus;
import com.minh.Online.Food.Ordering.domain.ports.in.order.GetOrderUseCase;
import com.minh.Online.Food.Ordering.domain.ports.in.order.ListOrdersUseCase;
import com.minh.Online.Food.Ordering.domain.ports.in.order.UpdateOrderStatusUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/restaurants/{restaurantId}/orders")
public class AdminOrderController {

    private final UpdateOrderStatusUseCase updateStatusUC;
    private final GetOrderUseCase getUC;
    private final ListOrdersUseCase listUC;

    @PatchMapping("/{orderId}/status")
    public ResponseEntity<OrderResponse> updateStatus(@PathVariable Long restaurantId,
                                                      @PathVariable Long orderId,
                                                      @RequestParam OrderStatus status){
        var saved = updateStatusUC.updateStatus(restaurantId, orderId, status);
        return ResponseEntity.ok(OrderWebMapper.toResponse(saved));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> get(@PathVariable Long restaurantId, @PathVariable Long orderId){
        return getUC.getByIdForRestaurant(restaurantId, orderId)
                .map(OrderWebMapper::toResponse).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    public List<OrderResponse> list(@PathVariable Long restaurantId,
                                    @RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "50") int size,
                                    @RequestParam(required = false) Long fromEpoch,
                                    @RequestParam(required = false) Long toEpoch){
        var from = fromEpoch != null ? Instant.ofEpochMilli(fromEpoch) : null;
        var to   = toEpoch   != null ? Instant.ofEpochMilli(toEpoch)   : null;
        return listUC.listForRestaurant(restaurantId, page, size, from, to).stream()
                .map(OrderWebMapper::toResponse).toList();
    }
}

