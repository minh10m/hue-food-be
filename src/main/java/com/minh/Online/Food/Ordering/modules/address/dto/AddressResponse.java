package com.minh.Online.Food.Ordering.modules.address.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AddressResponse {
    private Long id;
    private Long userId;
    private String phone;
    private String street;
    private String city;
    private boolean isDefault;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
