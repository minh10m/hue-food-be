package com.minh.Online.Food.Ordering.adapters.persistence.Ingredient;


import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.*;

public interface SpringDataIngredientCategoryRepository extends JpaRepository<IngredientCategoryJpaEntity, Long> {

    @Query("select c from IngredientCategoryJpaEntity c where c.id=:id and c.restaurant.id=:restaurantId")
    Optional<IngredientCategoryJpaEntity> findByIdAndRestaurantId(@Param("id") Long id, @Param("restaurantId") Long restaurantId);

    @Query("select c from IngredientCategoryJpaEntity c where c.restaurant.id=:restaurantId order by c.name asc")
    List<IngredientCategoryJpaEntity> findByRestaurantId(@Param("restaurantId") Long restaurantId);
}

