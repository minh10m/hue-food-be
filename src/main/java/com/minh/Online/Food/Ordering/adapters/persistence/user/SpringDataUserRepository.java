package com.minh.Online.Food.Ordering.adapters.persistence.user;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SpringDataUserRepository extends JpaRepository<UserJpaEntity, Long> {
    boolean existsByEmail(String email);
    boolean existsById(Long id);
    Optional<UserJpaEntity> findByEmail(String email);
}

