package com.minh.Online.Food.Ordering.repository;

import com.minh.Online.Food.Ordering.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
