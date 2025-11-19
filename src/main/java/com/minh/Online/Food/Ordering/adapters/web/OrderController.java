package com.minh.Online.Food.Ordering.adapters.web;

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

    // Thực tế: userId lấy từ JWT; demo nhận qua @RequestParam
    @PostMapping
    public ResponseEntity<OrderResponse> create(@RequestParam Long userId, @Valid @RequestBody OrderRequest req){
        var saved = createUC.create(OrderWebMapper.toDraft(userId, req));
        var dto = OrderWebMapper.toResponse(saved);
        return ResponseEntity.created(URI.create("/api/orders/"+dto.getId())).body(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> get(@RequestParam Long userId, @PathVariable Long id){
        return getUC.getByIdForUser(userId, id)
                .map(OrderWebMapper::toResponse).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    public List<OrderResponse> myOrders(@RequestParam Long userId,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "20") int size){
        return listUC.listForUser(userId, page, size).stream().map(OrderWebMapper::toResponse).toList();
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<OrderResponse> cancel(@RequestParam Long userId, @PathVariable Long id,
                                                @RequestParam(required=false) String reason){
        var saved = cancelUC.cancel(userId, id, reason);
        return ResponseEntity.ok(OrderWebMapper.toResponse(saved));
    }
}

