package com.minh.Online.Food.Ordering.modules.forgot_password;

import com.minh.Online.Food.Ordering.modules.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, Integer> {

    @Query("select fp from ForgotPassword fp where fp.otp = ?1 and fp.user = ?2")
    Optional<ForgotPassword> findByUserAndOtp(Integer otp, User user);

    Optional<ForgotPassword> findByUser(User user);
}