package com.minh.Online.Food.Ordering.adapters.persistence.Ingredient;


import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.*;

public interface SpringDataIngredientItemRepository extends JpaRepository<IngredientItemJpaEntity, Long> {

    @Query("select i from IngredientItemJpaEntity i where i.id=:id and i.restaurant.id=:restaurantId")
    Optional<IngredientItemJpaEntity> findByIdAndRestaurantId(@Param("id") Long id, @Param("restaurantId") Long restaurantId);

    @Query("select i from IngredientItemJpaEntity i where i.restaurant.id=:restaurantId order by i.name asc")
    List<IngredientItemJpaEntity> findByRestaurantId(@Param("restaurantId") Long restaurantId);

    @Query("select i from IngredientItemJpaEntity i where i.restaurant.id=:restaurantId and i.category.id=:categoryId order by i.name asc")
    List<IngredientItemJpaEntity> findByRestaurantIdAndCategoryId(@Param("restaurantId") Long restaurantId, @Param("categoryId") Long categoryId);
}