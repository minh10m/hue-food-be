package com.minh.Online.Food.Ordering.modules.ingredient.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.minh.Online.Food.Ordering.modules.restaurant.Restaurant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredientCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToOne
    @JsonIgnore
    private Restaurant restaurant;

    @JsonIgnore
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<IngredientsItem> ingredients = new ArrayList<>();
}
