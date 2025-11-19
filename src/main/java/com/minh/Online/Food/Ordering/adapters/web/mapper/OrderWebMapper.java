package com.minh.Online.Food.Ordering.adapters.web.mapper;

import com.minh.Online.Food.Ordering.adapters.web.dto.OrderRequest;
import com.minh.Online.Food.Ordering.adapters.web.dto.OrderResponse;
import com.minh.Online.Food.Ordering.domain.model.Order;
import com.minh.Online.Food.Ordering.domain.model.OrderItem;
import com.minh.Online.Food.Ordering.domain.model.OrderStatus;
import com.minh.Online.Food.Ordering.domain.model.PaymentMethod;

import java.time.Instant;
import java.util.stream.Collectors;

public final class OrderWebMapper {
    private OrderWebMapper(){}

    public static Order toDraft(Long userId, OrderRequest r){
        var items = r.getItems().stream().map(i ->
                new OrderItem(null, i.getFoodId(), i.getFoodName(), i.getUnitPrice(), i.getQuantity())
        ).toList();

        var subTotal = Order.calcSubTotal(items);
        var deliveryFee = r.getDeliveryFee() != null ? r.getDeliveryFee() : java.math.BigDecimal.ZERO;
        var total = subTotal.add(deliveryFee);

        var now = Instant.now();
        return new Order(
                null,
                userId,
                r.getRestaurantId(),
                items,
                subTotal,
                deliveryFee,
                total,
                r.getPaymentMethod() != null ? PaymentMethod.valueOf(r.getPaymentMethod()) : PaymentMethod.COD,
                OrderStatus.PENDING,
                now, now,
                r.getDeliveryAddress()
        );
    }

    public static OrderResponse toResponse(Order o){
        var d = new OrderResponse();
        d.setId(o.id()); d.setUserId(o.userId()); d.setRestaurantId(o.restaurantId());
        d.setItems(o.items().stream().map(it -> {
            var x = new OrderResponse.Item();
            x.setId(it.id()); x.setFoodId(it.foodId()); x.setFoodName(it.foodName());
            x.setUnitPrice(it.unitPrice()); x.setQuantity(it.quantity()); x.setLineTotal(it.lineTotal());
            return x;
        }).collect(Collectors.toList()));
        d.setSubTotal(o.subTotal()); d.setDeliveryFee(o.deliveryFee()); d.setTotal(o.total());
        d.setPaymentMethod(o.paymentMethod().name()); d.setStatus(o.status().name());
        d.setCreatedAt(o.createdAt()); d.setUpdatedAt(o.updatedAt());
        d.setDeliveryAddress(o.deliveryAddress());
        return d;
    }
}

