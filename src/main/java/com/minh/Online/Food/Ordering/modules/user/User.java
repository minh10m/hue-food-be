package com.minh.Online.Food.Ordering.modules.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.minh.Online.Food.Ordering.modules.forgot_password.ForgotPassword;
import com.minh.Online.Food.Ordering.modules.restaurant.dto.RestaurantDto;
import com.minh.Online.Food.Ordering.modules.address.Address;
import com.minh.Online.Food.Ordering.modules.order.model.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "\"user\"")
public class User implements UserDetails {

    @Id
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    private Long id;

    private String fullName;

    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Enumerated(EnumType.STRING)
    private USER_ROLE role;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role != null ? role.toString() : USER_ROLE.ROLE_CUSTOMER.toString()));
    }
    
    @Override
    public String getUsername() {
        return email;
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    
    @Override
    public boolean isEnabled() {
        return true;
    }

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private List<Order> orders = new ArrayList<>();

    @ElementCollection
    private List<RestaurantDto> favorites = new ArrayList<>();

    @JsonIgnore
    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Address> addresses = new ArrayList<>();

    @OneToOne(mappedBy = "user")
    private ForgotPassword forgotPassword;
}
