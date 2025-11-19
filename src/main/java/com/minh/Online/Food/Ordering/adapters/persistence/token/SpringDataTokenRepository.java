package com.minh.Online.Food.Ordering.adapters.persistence.token;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.Optional;

public interface SpringDataTokenRepository extends JpaRepository<TokenJpaEntity, Long> {
    Optional<TokenJpaEntity> findByToken(String token);

    @Modifying
    @Query("update TokenJpaEntity t set t.revoked=true where t.userId=:userId and t.revoked=false")
    void revokeAllByUserId(@Param("userId") Long userId);

    @Modifying
    @Query("update TokenJpaEntity t set t.revoked=true where t.token=:token")
    void revokeOne(@Param("token") String token);

    @Query("select case when (count(t)>0) then true else false end from TokenJpaEntity t " +
            "where t.token=:token and t.revoked=false and t.expiresAt > :now")
    boolean isActive(@Param("token") String token, @Param("now") Instant now);
}
