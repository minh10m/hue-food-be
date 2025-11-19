package com.minh.Online.Food.Ordering.adapters.persistence.address;

import com.minh.Online.Food.Ordering.adapters.persistence.user.UserJpaEntity;
import jakarta.persistence.*;
import lombok.Getter; import lombok.Setter; import lombok.NoArgsConstructor; import lombok.AllArgsConstructor; import lombok.Builder;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name = "address")
public class AddressJpaEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id")
    private UserJpaEntity user;

    private String phone;
    private String street;
    private String city;

    @Column(name = "is_default")
    private boolean isDefault;
}

