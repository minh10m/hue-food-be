package com.minh.Online.Food.Ordering.adapters.persistence.event;

import com.minh.Online.Food.Ordering.domain.model.EventStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name = "events")
public class EventJpaEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="restaurant_id", nullable=false)
    private Long restaurantId;

    @Column(nullable=false) private String title;
    @Column(length=4000) private String description;
    @Column(nullable=false) private Instant startsAt;
    @Column(nullable=false) private Instant endsAt;
    private String imageUrl;

    @Enumerated(EnumType.STRING) @Column(nullable=false)
    private EventStatus status;

    @Column(nullable=false) private Instant createdAt;
    @Column(nullable=false) private Instant updatedAt;
}

