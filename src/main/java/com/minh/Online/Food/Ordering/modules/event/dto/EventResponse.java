package com.minh.Online.Food.Ordering.modules.event.dto;

import com.minh.Online.Food.Ordering.modules.event.EventStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data @Builder
public class EventResponse {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String image;             // 🔹 single
    private EventStatus status;
    private Long restaurantId;
}
