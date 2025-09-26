package com.minh.Online.Food.Ordering.modules.category.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.minh.Online.Food.Ordering.modules.restaurant.Restaurant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToOne
    @JsonIgnore
    private Restaurant restaurant;
}
