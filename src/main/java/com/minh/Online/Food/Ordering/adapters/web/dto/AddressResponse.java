package com.minh.Online.Food.Ordering.adapters.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressResponse {
    @Setter
    private Long id;
    @Getter
    @Setter
    private Long userId;
    @Getter
    @Setter
    private String phone;
    @Setter
    @Getter
    private String street;
    @Getter
    @Setter
    private String city;
    private boolean isDefault;

    public boolean isDefault() { return isDefault; }
    public void setDefault(boolean aDefault) { isDefault = aDefault; }
}

