package com.minh.Online.Food.Ordering.adapters.web.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AddCartItemRequest {

    @NotNull(message = "ID món ăn là bắt buộc")
    private Long foodId;

    @NotBlank(message = "Tên món ăn không được để trống")
    private String foodName;

    @NotNull(message = "Giá món ăn là bắt buộc")
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá món ăn phải lớn hơn 0")
    private BigDecimal unitPrice;

    @Min(value = 1, message = "Số lượng phải lớn hơn hoặc bằng 1")
    private int quantity;

    @NotNull(message = "ID nhà hàng là bắt buộc")
    private Long restaurantId;
}

