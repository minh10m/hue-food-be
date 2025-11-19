package com.minh.Online.Food.Ordering.adapters.persistence.order;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.*;

public interface SpringDataOrderRepository extends JpaRepository<OrderJpaEntity, Long> {

    @Query("select o from OrderJpaEntity o left join fetch o.items where o.id=:id and o.userId=:userId")
    Optional<OrderJpaEntity> findByIdAndUserIdFetch(@Param("id") Long id, @Param("userId") Long userId);

    @Query("select o from OrderJpaEntity o left join fetch o.items where o.id=:id and o.restaurantId=:restaurantId")
    Optional<OrderJpaEntity> findByIdAndRestaurantIdFetch(@Param("id") Long id, @Param("restaurantId") Long restaurantId);

    @EntityGraph(attributePaths = "items")
    Page<OrderJpaEntity> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);

    @Query("""
           select o from OrderJpaEntity o
           where o.restaurantId=:restaurantId
             and (:from is null or o.createdAt >= :from)
             and (:to   is null or o.createdAt <= :to)
           order by o.createdAt desc
           """)
    Page<OrderJpaEntity> findByRestaurant(@Param("restaurantId") Long restaurantId,
                                          @Param("from") Instant from,
                                          @Param("to") Instant to,
                                          Pageable pageable);
}

