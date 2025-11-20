package com.minh.Online.Food.Ordering.adapters.web.dto;


import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class CreateFoodRequest {

    @NotBlank(message = "Tên món ăn không được để trống")
    private String name;

    @Size(max = 2000, message = "Mô tả không được vượt quá 2000 ký tự")
    private String description;

    @NotNull(message = "Giá món ăn là bắt buộc")
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá món ăn phải lớn hơn 0")
    private BigDecimal price;

    private Boolean vegetarian = false;

    @Size(max = 500, message = "Đường dẫn hình ảnh không hợp lệ")
    private String imageUrl;

    private Long categoryId;
}


