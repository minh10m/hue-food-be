package com.minh.Online.Food.Ordering.adapters.persistence.event;

import com.minh.Online.Food.Ordering.domain.model.EventStatus;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface SpringDataEventRepository extends JpaRepository<EventJpaEntity, Long> {

    @Query("select e from EventJpaEntity e where e.id=:id and e.restaurantId=:restaurantId")
    Optional<EventJpaEntity> findByIdAndRestaurantId(@Param("id") Long id, @Param("restaurantId") Long restaurantId);

    @Query("""
           select e from EventJpaEntity e
           where e.status = :published
             and e.startsAt >= :from
           order by e.startsAt asc
           """)
    Page<EventJpaEntity> findUpcomingPublic(@Param("published") EventStatus published,
                                            @Param("from") Instant from, Pageable pageable);

    List<EventJpaEntity> findByRestaurantIdOrderByStartsAtDesc(Long restaurantId);

    @Query("""
           select e from EventJpaEntity e
           where e.restaurantId = :restaurantId
             and e.startsAt >= :from and e.endsAt <= :to
           order by e.startsAt asc
           """)
    List<EventJpaEntity> findByRestaurantInRange(@Param("restaurantId") Long restaurantId,
                                                 @Param("from") Instant from,
                                                 @Param("to") Instant to);
}

