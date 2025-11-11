package com.minh.Online.Food.Ordering.modules.event.dto;

import com.minh.Online.Food.Ordering.modules.event.EventStatus;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateEventRequest {
    @NotBlank private String title;
    private String description;
    @NotNull private LocalDateTime startAt;
    @NotNull private LocalDateTime endAt;

    // 🔹 1 ảnh duy nhất
    @Size(max = 512) private String image;

    // optional, default ACTIVE nếu null
    private EventStatus status;
}
