package com.minh.Online.Food.Ordering.modules.address;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.minh.Online.Food.Ordering.modules.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
@Entity
@Table(
        name = "address",
        indexes = {
                @Index(name = "idx_address_user_id", columnList = "user_id"),
                @Index(name = "idx_address_is_default", columnList = "is_default")
        }
)
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_seq")
    @SequenceGenerator(name = "address_seq", sequenceName = "address_seq", allocationSize = 1)
    private Long id;

    /* Chủ sở hữu địa chỉ: 1 User -> N Address */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")      // cột FK ở bảng address
    @JsonIgnore
    private User user;

    @Size(max = 20)
    private String phone;

    @NotBlank
    @Size(max = 150)
    private String street;     // số nhà, tên đường

    @Size(max = 100)
    private String city;       // TP/Thị xã

    private boolean isDefault = false;

//    private LocalDateTime createdAt;
//    private LocalDateTime updatedAt;
//
//    @PrePersist
//    public void prePersist() {
//        if (createdAt == null) createdAt = LocalDateTime.now();
//        if (updatedAt == null) updatedAt = createdAt;
//    }
//
//    @PreUpdate
//    public void preUpdate() {
//        updatedAt = LocalDateTime.now();
//    }
}
