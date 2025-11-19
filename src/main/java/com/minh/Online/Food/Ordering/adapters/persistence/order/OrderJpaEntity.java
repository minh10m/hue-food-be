package com.minh.Online.Food.Ordering.adapters.persistence.order;

import com.minh.Online.Food.Ordering.domain.model.OrderStatus;
import com.minh.Online.Food.Ordering.domain.model.PaymentMethod;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name = "orders")
public class OrderJpaEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="user_id", nullable=false) private Long userId;
    @Column(name="restaurant_id", nullable=false) private Long restaurantId;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItemJpaEntity> items = new ArrayList<>();

    @Column(nullable=false, precision=18, scale=2) private BigDecimal subTotal;
    @Column(nullable=false, precision=18, scale=2) private BigDecimal deliveryFee;
    @Column(nullable=false, precision=18, scale=2) private BigDecimal total;

    @Enumerated(EnumType.STRING) @Column(nullable=false) private PaymentMethod paymentMethod;
    @Enumerated(EnumType.STRING) @Column(nullable=false) private OrderStatus status;

    @Column(nullable=false) private Instant createdAt;
    @Column(nullable=false) private Instant updatedAt;

    @Column(length=1000) private String deliveryAddress;
}

