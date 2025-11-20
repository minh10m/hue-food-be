package com.minh.Online.Food.Ordering.adapters.persistence.food;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SpringDataFoodRepository extends JpaRepository<FoodJpaEntity, Long> {

    @Query("select f from FoodJpaEntity f where f.id=:id and f.restaurantId in " +
           "(select r.id from RestaurantJpaEntity r where r.owner.id=:ownerId)")
    Optional<FoodJpaEntity> findByIdAndOwnerId(@Param("id") Long id, @Param("ownerId") Long ownerId);

    @Query("""
    select f from FoodJpaEntity f
    where (:restaurantId is null or f.restaurantId = :restaurantId)
    and (:categoryId  is null or f.categoryId  = :categoryId)
    and f.availability = :availability
    """)
    Page<FoodJpaEntity> searchPublic(@Param("restaurantId") Long restaurantId,
                                     @Param("categoryId")  Long categoryId,
                                     @Param("availability") com.minh.Online.Food.Ordering.domain.model.FoodAvailability availability,
                                     Pageable pageable);


    @Query("select f from FoodJpaEntity f where f.restaurantId in (select r.id from RestaurantJpaEntity r where r.owner.id=:ownerId) order by f.updatedAt desc")
    List<FoodJpaEntity> findByOwner(@Param("ownerId") Long ownerId);

    @Query("select f from FoodJpaEntity f where f.restaurantId=:restaurantId and :ownerId in (select r.owner.id from RestaurantJpaEntity r where r.id=:restaurantId)")
    List<FoodJpaEntity> findByOwnerAndRestaurant(@Param("ownerId") Long ownerId, @Param("restaurantId") Long restaurantId);
}
