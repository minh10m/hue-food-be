package com.minh.Online.Food.Ordering.modules.order.repository;

import com.minh.Online.Food.Ordering.modules.order.model.Order;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByCustomerId(Long userId);

    public List<Order> findByRestaurantId(Long restaurantId);

    @Query("""
        select distinct o from Order o
        join fetch o.items i
        join fetch i.food f
        join fetch o.restaurant r
        where o.id = :id
    """)
    Optional<Order> findByIdWithItemsFoodAndRestaurant(@Param("id") Long id);
}
