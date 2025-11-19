package com.minh.Online.Food.Ordering.adapters.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AddressRequest {

    @NotBlank(message = "Số điện thoại là bắt buộc")
    @Size(max = 20)
    private String phone;

    @NotBlank(message = "Địa chỉ là bắt buộc")
    @Size(max = 255)
    private String street;

    @NotBlank(message = "Thành phố là bắt buộc")
    @Size(max = 100)
    private String city;

    private boolean isDefault;

    // getters/setters
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public boolean isDefault() { return isDefault; }
    public void setDefault(boolean aDefault) { isDefault = aDefault; }
}

