package com.minh.Online.Food.Ordering.adapters.web.dto;

import lombok.Getter; import lombok.Setter;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Getter @Setter
public class OrderResponse {
    private Long id;
    private Long userId;
    private Long restaurantId;
    private List<Item> items;
    private BigDecimal subTotal;
    private BigDecimal deliveryFee;
    private BigDecimal total;
    private String paymentMethod;
    private String status;
    private Instant createdAt;
    private Instant updatedAt;
    private String deliveryAddress;

    @Getter @Setter
    public static class Item {
        private Long id;
        private Long foodId;
        private String foodName;
        private BigDecimal unitPrice;
        private int quantity;
        private BigDecimal lineTotal;
    }
}

