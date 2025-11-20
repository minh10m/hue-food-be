package com.minh.Online.Food.Ordering.adapters.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

public class AddressRequest {

    // getters/setters
    @Setter
    @Getter
    @NotBlank(message = "Số điện thoại là bắt buộc")
    @Size(max = 20)
    private String phone;

    @Setter
    @Getter
    @NotBlank(message = "Địa chỉ là bắt buộc")
    @Size(max = 255)
    private String street;

    @Setter
    @Getter
    @NotBlank(message = "Thành phố là bắt buộc")
    @Size(max = 100)
    private String city;

    private boolean isDefault;

    public boolean isDefault() { return isDefault; }
    public void setDefault(boolean aDefault) { isDefault = aDefault; }
}

