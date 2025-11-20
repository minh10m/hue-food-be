package com.minh.Online.Food.Ordering.adapters.persistence.order;

import com.minh.Online.Food.Ordering.domain.model.Order;
import com.minh.Online.Food.Ordering.domain.model.OrderItem;
import com.minh.Online.Food.Ordering.domain.ports.out.OrderRepositoryPort;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class OrderRepositoryAdapter implements OrderRepositoryPort {

    private final SpringDataOrderRepository repo;

    public OrderRepositoryAdapter(SpringDataOrderRepository repo) { this.repo = repo; }

    @Override @Transactional
    public Order save(Order o) {
        var e = toEntity(o);
        // sync items
        OrderJpaEntity target = (e.getId() != null)
                ? repo.findById(e.getId()).orElse(new OrderJpaEntity())
                : new OrderJpaEntity();
        target.setUserId(e.getUserId());
        target.setRestaurantId(e.getRestaurantId());
        target.setSubTotal(e.getSubTotal());
        target.setDeliveryFee(e.getDeliveryFee());
        target.setTotal(e.getTotal());
        target.setPaymentMethod(e.getPaymentMethod());
        target.setStatus(e.getStatus());
        target.setCreatedAt(e.getCreatedAt());
        target.setUpdatedAt(e.getUpdatedAt());
        target.setDeliveryAddress(e.getDeliveryAddress());

        target.getItems().clear();
        for (var it : e.getItems()){
            it.setOrder(target);
            target.getItems().add(it);
        }
        var saved = repo.save(target);
        return toDomain(saved);
    }

    @Override public Optional<Order> findById(Long id){ return repo.findById(id).map(this::toDomain); }

    @Override
    public Optional<Order> findByIdAndUserId(Long id, Long userId) {
        return repo.findByIdAndUserIdFetch(id, userId).map(this::toDomain);
    }

    @Override
    public Optional<Order> findByIdAndRestaurantId(Long id, Long restaurantId) {
        return repo.findByIdAndRestaurantIdFetch(id, restaurantId).map(this::toDomain);
    }

    @Override
    public List<Order> findByUser(Long userId, int page, int size) {
        return repo.findByUserIdOrderByCreatedAtDesc(userId, PageRequest.of(page, size))
                .map(this::toDomain).getContent();
    }

    @Override
    public List<Order> findByRestaurant(Long restaurantId, int page, int size, Instant from, Instant to) {
        return repo.findByRestaurant(restaurantId, from, to, PageRequest.of(page, size))
                .map(this::toDomain).getContent();
    }

    // ===== Mapping =====
    private Order toDomain(OrderJpaEntity e){
        var items = e.getItems().stream().map(i ->
                new OrderItem(i.getId(), i.getFoodId(), i.getFoodName(), i.getUnitPrice(), i.getQuantity())
        ).collect(Collectors.toList());

        return new Order(
                e.getId(), e.getUserId(), e.getRestaurantId(), items,
                e.getSubTotal(), e.getDeliveryFee(), e.getTotal(),
                e.getPaymentMethod(), e.getStatus(),
                e.getCreatedAt(), e.getUpdatedAt(), e.getDeliveryAddress()
        );
    }

    private OrderJpaEntity toEntity(Order o){
        var e = OrderJpaEntity.builder()
                .id(o.id())
                .userId(o.userId())
                .restaurantId(o.restaurantId())
                .subTotal(o.subTotal())
                .deliveryFee(o.deliveryFee())
                .total(o.total())
                .paymentMethod(o.paymentMethod())
                .status(o.status())
                .createdAt(o.createdAt())
                .updatedAt(o.updatedAt())
                .deliveryAddress(o.deliveryAddress())
                .items(new ArrayList<>())
                .build();
        for (OrderItem it : o.items()){
            var ie = OrderItemJpaEntity.builder()
                    .id(it.id())
                    .foodId(it.foodId())
                    .foodName(it.foodName())
                    .unitPrice(it.unitPrice())
                    .quantity(it.quantity())
                    .build();
            ie.setOrder(e);
            e.getItems().add(ie);
        }
        return e;
    }
}
