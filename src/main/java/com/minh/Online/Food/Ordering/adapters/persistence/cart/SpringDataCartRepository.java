package com.minh.Online.Food.Ordering.adapters.persistence.cart;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SpringDataCartRepository extends JpaRepository<CartJpaEntity, Long> {

    @Query("select c from CartJpaEntity c left join fetch c.items where c.userId=:userId")
    Optional<CartJpaEntity> findWithItemsByUserId(@Param("userId") Long userId);

    Optional<CartJpaEntity> findByUserId(Long userId);
}
