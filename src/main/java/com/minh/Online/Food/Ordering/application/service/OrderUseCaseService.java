package com.minh.Online.Food.Ordering.application.service;

import com.minh.Online.Food.Ordering.domain.model.Order;
import com.minh.Online.Food.Ordering.domain.model.OrderStatus;
import com.minh.Online.Food.Ordering.domain.model.PaymentMethod;
import com.minh.Online.Food.Ordering.domain.ports.in.order.*;
import com.minh.Online.Food.Ordering.domain.ports.out.OrderRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

@Service
public class OrderUseCaseService implements
        CreateOrderUseCase, UpdateOrderStatusUseCase, CancelOrderUseCase, GetOrderUseCase, ListOrdersUseCase {

    private final OrderRepositoryPort repo;

    public OrderUseCaseService(OrderRepositoryPort repo) { this.repo = repo; }

    @Override @Transactional
    public Order create(Order draft) {
        if (draft.userId() == null || draft.restaurantId() == null) throw new IllegalArgumentException("userId/restaurantId required");
        if (draft.items() == null || draft.items().isEmpty()) throw new IllegalArgumentException("items required");
        var now = Instant.now();
        var subTotal = Order.calcSubTotal(draft.items());
        var deliveryFee = draft.deliveryFee() != null ? draft.deliveryFee() : BigDecimal.ZERO;
        var total = subTotal.add(deliveryFee);

        var toSave = new Order(null, draft.userId(), draft.restaurantId(), draft.items(),
                subTotal, deliveryFee, total,
                draft.paymentMethod() != null ? draft.paymentMethod() : PaymentMethod.COD,
                OrderStatus.PENDING, now, now, draft.deliveryAddress());

        return repo.save(toSave);
    }

    @Override @Transactional
    public Order updateStatus(Long restaurantId, Long orderId, OrderStatus status) {
        var now = Instant.now();
        var o = repo.findByIdAndRestaurantId(orderId, restaurantId)
                .orElseThrow(() -> new NoSuchElementException("Order not found"));
        if (o.status() == OrderStatus.CANCELLED || o.status() == OrderStatus.COMPLETED)
            throw new IllegalStateException("Cannot change status for finished order");
        return repo.save(o.withStatus(status, now));
    }

    @Override @Transactional
    public Order cancel(Long userId, Long orderId, String reason) {
        var now = Instant.now();
        var o = repo.findByIdAndUserId(orderId, userId)
                .orElseThrow(() -> new NoSuchElementException("Order not found"));
        if (o.status() == OrderStatus.COMPLETED) throw new IllegalStateException("Completed order cannot be cancelled");
        return repo.save(o.withStatus(OrderStatus.CANCELLED, now));
    }

    @Override public Optional<Order> getByIdForUser(Long userId, Long orderId) {
        return repo.findByIdAndUserId(orderId, userId);
    }
    @Override public Optional<Order> getByIdForRestaurant(Long restaurantId, Long orderId) {
        return repo.findByIdAndRestaurantId(orderId, restaurantId);
    }

    @Override public List<Order> listForUser(Long userId, int page, int size) {
        return repo.findByUser(userId, page, size);
    }
    @Override public List<Order> listForRestaurant(Long restaurantId, int page, int size, Instant from, Instant to) {
        return repo.findByRestaurant(restaurantId, page, size, from, to);
    }
}

