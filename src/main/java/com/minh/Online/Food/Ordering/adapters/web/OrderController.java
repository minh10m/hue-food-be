package com.minh.Online.Food.Ordering.adapters.web;

import com.minh.Online.Food.Ordering.adapters.security.AuthUtils;
import com.minh.Online.Food.Ordering.adapters.web.dto.OrderRequest;
import com.minh.Online.Food.Ordering.adapters.web.dto.OrderResponse;
import com.minh.Online.Food.Ordering.adapters.web.mapper.OrderWebMapper;
import com.minh.Online.Food.Ordering.domain.ports.in.order.CancelOrderUseCase;
import com.minh.Online.Food.Ordering.domain.ports.in.order.CreateOrderUseCase;
import com.minh.Online.Food.Ordering.domain.ports.in.order.GetOrderUseCase;
import com.minh.Online.Food.Ordering.domain.ports.in.order.ListOrdersUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final CreateOrderUseCase createUC;
    private final CancelOrderUseCase cancelUC;
    private final GetOrderUseCase getUC;
    private final ListOrdersUseCase listUC;

    private final AuthUtils auth;

    @PostMapping
    public ResponseEntity<OrderResponse> create(Authentication authentication, @Valid @RequestBody OrderRequest req){
        Long userId = auth.currentUserId(authentication);
        var saved = createUC.create(OrderWebMapper.toDraft(userId, req));
        var dto = OrderWebMapper.toResponse(saved);
        return ResponseEntity.created(URI.create("/api/orders/"+dto.getId())).body(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> get(Authentication authentication, @PathVariable Long id){
        Long userId = auth.currentUserId(authentication);
        return getUC.getByIdForUser(userId, id)
                .map(OrderWebMapper::toResponse).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    public List<OrderResponse> myOrders(Authentication authentication,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "20") int size){
        Long userId = auth.currentUserId(authentication);
        return listUC.listForUser(userId, page, size).stream().map(OrderWebMapper::toResponse).toList();
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<OrderResponse> cancel(Authentication authentication, @PathVariable Long id,
                                                @RequestParam(required=false) String reason){
        Long userId = auth.currentUserId(authentication);
        var saved = cancelUC.cancel(userId, id, reason);
        return ResponseEntity.ok(OrderWebMapper.toResponse(saved));
    }
}

