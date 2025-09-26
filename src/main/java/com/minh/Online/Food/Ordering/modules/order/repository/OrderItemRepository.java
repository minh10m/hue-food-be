package com.minh.Online.Food.Ordering.modules.order.repository;

import com.minh.Online.Food.Ordering.modules.order.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
