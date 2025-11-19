package com.minh.Online.Food.Ordering.adapters.persistence.order;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name = "order_items")
public class OrderItemJpaEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="order_id", nullable=false)
    private OrderJpaEntity order;

    @Column(name="food_id", nullable=false) private Long foodId;
    private String foodName;

    @Column(nullable=false, precision=18, scale=2)
    private BigDecimal unitPrice;

    @Column(nullable=false)
    private int quantity;
}

