package com.minh.Online.Food.Ordering.adapters.web.dto;

import lombok.Getter; import lombok.Setter;
import java.math.BigDecimal;
import java.util.List;

@Getter @Setter
public class CartResponse {
    private Long cartId;
    private Long userId;
    private BigDecimal total;
    private List<Item> items;

    @Getter @Setter
    public static class Item {
        private Long id;
        private Long foodId;
        private String foodName;
        private BigDecimal unitPrice;
        private int quantity;
        private BigDecimal lineTotal;
        private Long restaurantId;
    }
}

