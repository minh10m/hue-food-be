package com.minh.Online.Food.Ordering.modules.cart.model;

import com.minh.Online.Food.Ordering.modules.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    @Id
    @SequenceGenerator(name = "cart_seq", sequenceName = "cart_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_seq")
    private Long id;

    @OneToOne
    private User customer;

    private Long total;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartItem> items = new ArrayList<>();
}
