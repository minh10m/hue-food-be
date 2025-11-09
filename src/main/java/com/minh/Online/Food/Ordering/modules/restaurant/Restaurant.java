package com.minh.Online.Food.Ordering.modules.restaurant;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.minh.Online.Food.Ordering.modules.address.Address;
import com.minh.Online.Food.Ordering.modules.address.dto.ContactInformation;
import com.minh.Online.Food.Ordering.modules.user.User;
import com.minh.Online.Food.Ordering.modules.food.Food;
import com.minh.Online.Food.Ordering.modules.order.model.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonIgnore
    @ManyToOne
    private User owner;

    private String name;

    private String description;

    private String cuisineType;

    @OneToOne
    private Address address;

    @Embedded
    private ContactInformation contactInformation;

    private String openingHours;

    @JsonIgnore
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    @Column(length = 1000)
    private String image;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate registrationDate;

    private boolean open;

    @JsonIgnore
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<Food> foods = new ArrayList<>();

    @Override public boolean equals(Object o){ if(this==o) return true; if(!(o instanceof Restaurant r)) return false; return id!=null && id.equals(r.id); }
    @Override public int hashCode(){ return Objects.hashCode(id); }
}
