package com.minh.Online.Food.Ordering.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

public final class Order {
    private final Long id;
    private final Long userId;
    private final Long restaurantId;
    private final List<OrderItem> items;
    private final BigDecimal subTotal;
    private final BigDecimal deliveryFee;
    private final BigDecimal total;
    private final PaymentMethod paymentMethod;
    private final OrderStatus status;
    private final Instant createdAt;
    private final Instant updatedAt;
    private final String deliveryAddress;   // đơn giản hoá: snapshot địa chỉ giao

    public Order(Long id, Long userId, Long restaurantId, List<OrderItem> items,
                 BigDecimal subTotal, BigDecimal deliveryFee, BigDecimal total,
                 PaymentMethod paymentMethod, OrderStatus status,
                 Instant createdAt, Instant updatedAt, String deliveryAddress) {
        this.id = id; this.userId = userId; this.restaurantId = restaurantId;
        this.items = items != null ? List.copyOf(items) : List.of();
        this.subTotal = subTotal; this.deliveryFee = deliveryFee; this.total = total;
        this.paymentMethod = paymentMethod; this.status = status;
        this.createdAt = createdAt; this.updatedAt = updatedAt; this.deliveryAddress = deliveryAddress;
    }

    public Long id(){ return id; }
    public Long userId(){ return userId; }
    public Long restaurantId(){ return restaurantId; }
    public List<OrderItem> items(){ return items; }
    public BigDecimal subTotal(){ return subTotal; }
    public BigDecimal deliveryFee(){ return deliveryFee; }
    public BigDecimal total(){ return total; }
    public PaymentMethod paymentMethod(){ return paymentMethod; }
    public OrderStatus status(){ return status; }
    public Instant createdAt(){ return createdAt; }
    public Instant updatedAt(){ return updatedAt; }
    public String deliveryAddress(){ return deliveryAddress; }

    public Order withId(Long newId){ return new Order(newId, userId, restaurantId, items, subTotal, deliveryFee, total, paymentMethod, status, createdAt, updatedAt, deliveryAddress); }
    public Order withStatus(OrderStatus s, Instant now){
        return new Order(id, userId, restaurantId, items, subTotal, deliveryFee, total, paymentMethod, s, createdAt, now, deliveryAddress);
    }

    public static BigDecimal calcSubTotal(List<OrderItem> items){
        return items.stream().map(OrderItem::lineTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}

