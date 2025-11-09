package com.minh.Online.Food.Ordering.modules.user;

import io.lettuce.core.dynamic.annotation.Param;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    
    boolean existsByEmail(String email);
    
    @Transactional
    @Modifying
    @Query("update User u set u.password = ?2 where u.email = ?1")
    void updatePassword(String email, String password);

    @EntityGraph(attributePaths = "favorites")
    Optional<User> findById(Long id);
    @Query("select u from User u left join fetch u.favorites where u.id = :id")
    Optional<User> findByIdWithFavorites(@Param("id") Long id);
}
