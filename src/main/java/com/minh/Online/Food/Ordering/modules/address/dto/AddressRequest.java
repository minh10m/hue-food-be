package com.minh.Online.Food.Ordering.modules.address.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AddressRequest {
    @Size(max = 20)
    private String phone;

    @NotBlank
    @Size(max = 150)
    private String street;

    @Size(max = 100)
    private String city;

    private boolean isDefault; // nếu true, sẽ đặt làm mặc định (và clear cái cũ)
}
