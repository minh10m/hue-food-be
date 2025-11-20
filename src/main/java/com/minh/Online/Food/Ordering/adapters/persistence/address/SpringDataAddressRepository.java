package com.minh.Online.Food.Ordering.adapters.persistence.address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SpringDataAddressRepository extends JpaRepository<AddressJpaEntity, Long> {
    List<AddressJpaEntity> findByUserIdOrderByIsDefaultDescIdAsc(Long userId);

    Optional<AddressJpaEntity> findByIdAndUserId(Long id, Long userId);

    @Modifying
    @Query("update AddressJpaEntity a set a.isDefault=false where a.user.id = :userId and a.isDefault = true")
    void clearDefaultForUser(Long userId);
}

