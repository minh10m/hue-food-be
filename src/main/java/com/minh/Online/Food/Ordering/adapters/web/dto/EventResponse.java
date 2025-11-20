package com.minh.Online.Food.Ordering.adapters.web.dto;

import lombok.Getter; import lombok.Setter;

import java.time.Instant;

@Getter @Setter
public class EventResponse {
    private Long id;
    private Long restaurantId;
    private String title;
    private String description;
    private Instant startsAt;
    private Instant endsAt;
    private String imageUrl;
    private String status;
    private Instant createdAt;
    private Instant updatedAt;
}
