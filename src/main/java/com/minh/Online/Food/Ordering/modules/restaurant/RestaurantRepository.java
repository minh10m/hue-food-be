package com.minh.Online.Food.Ordering.modules.restaurant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    @Query("SELECT r from Restaurant r WHERE lower(r.name) LIKE lower(concat('%', :query, '%') ) " +
            "OR lower(r.cuisineType) LIKE lower(concat('%', :query, '%') )")
    List<Restaurant> findBySearchQuery(String query);

    Restaurant findByOwnerId(Long userId);

    @Query("""
  select r from Restaurant r
  left join fetch r.owner
  left join fetch r.address""")
    List<Restaurant> findAllWithOwnerAndAddress();

}
