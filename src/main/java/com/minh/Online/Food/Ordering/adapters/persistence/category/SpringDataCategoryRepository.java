package com.minh.Online.Food.Ordering.adapters.persistence.category;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.*;

public interface SpringDataCategoryRepository extends JpaRepository<CategoryJpaEntity, Long> {

    @Query("select c from CategoryJpaEntity c where c.id=:id and c.restaurantId=:restaurantId")
    Optional<CategoryJpaEntity> findByIdAndRestaurantId(@Param("id") Long id, @Param("restaurantId") Long restaurantId);

    List<CategoryJpaEntity> findByRestaurantIdOrderByNameAsc(Long restaurantId);
}

