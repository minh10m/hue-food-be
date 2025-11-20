package com.minh.Online.Food.Ordering.adapters.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter; import lombok.Setter;

import java.time.Instant;

@Getter @Setter
public class CreateEventRequest {
    @NotBlank private String title;
    private String description;
    @NotNull private Instant startsAt;
    @NotNull private Instant endsAt;
    private String imageUrl;
    // status bỏ trống để service set mặc định (DRAFT)
}

