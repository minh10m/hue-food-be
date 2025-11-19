package com.minh.Online.Food.Ordering.adapters.web.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class OrderRequest {

    @NotNull(message = "restaurantId là bắt buộc")
    private Long restaurantId;

    @NotEmpty(message = "Danh sách items không được rỗng")
    @Valid
    private List<Item> items;

    @DecimalMin(value = "0.0", inclusive = true, message = "deliveryFee không hợp lệ")
    private BigDecimal deliveryFee;

    @Pattern(regexp = "COD|CARD|WALLET", message = "paymentMethod phải là COD, CARD hoặc WALLET")
    private String paymentMethod;

    @NotBlank(message = "deliveryAddress là bắt buộc")
    @Size(max = 1000, message = "deliveryAddress tối đa 1000 ký tự")
    private String deliveryAddress;

    @Getter
    @Setter
    public static class Item {
        @NotNull(message = "foodId là bắt buộc")
        private Long foodId;

        @Size(max = 255, message = "foodName tối đa 255 ký tự")
        private String foodName;

        @NotNull(message = "unitPrice là bắt buộc")
        @DecimalMin(value = "0.0", inclusive = false, message = "unitPrice phải > 0")
        private BigDecimal unitPrice;

        @Min(value = 1, message = "quantity phải >= 1")
        private int quantity;
    }
}

