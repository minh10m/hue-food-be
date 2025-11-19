package com.minh.Online.Food.Ordering.adapters.persistence.restaurant;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SpringDataRestaurantRepository extends JpaRepository<RestaurantJpaEntity, Long> {

    @Query("select r from RestaurantJpaEntity r where r.id=:id and r.owner.id=:ownerId")
    Optional<RestaurantJpaEntity> findByIdAndOwnerId(@Param("id") Long id, @Param("ownerId") Long ownerId);

    @Query("select r from RestaurantJpaEntity r where r.owner.id=:ownerId order by r.updatedAt desc")
    List<RestaurantJpaEntity> findByOwnerId(@Param("ownerId") Long ownerId);

    @Query("""
           select r from RestaurantJpaEntity r
           where r.status = com.minh.Online.Food.Ordering.adapters.persistence.restaurant.RestaurantJpaEntity$Status.OPEN
             and (:city is null or lower(r.city) = lower(:city))
             and (:cuisine is null or lower(r.cuisineType) = lower(:cuisine))
           """)
    Page<RestaurantJpaEntity> searchPublic(@Param("city") String city,
                                           @Param("cuisine") String cuisine,
                                           Pageable pageable);
}

