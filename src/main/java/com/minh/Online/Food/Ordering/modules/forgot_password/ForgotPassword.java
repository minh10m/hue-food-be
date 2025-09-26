package com.minh.Online.Food.Ordering.modules.forgot_password;

import com.minh.Online.Food.Ordering.modules.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ForgotPassword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fpid;

    @Column(nullable = false)
    private Date expirationTime;

    @Column(nullable = false)
    private Integer otp;

    @OneToOne
    private User user;
}
