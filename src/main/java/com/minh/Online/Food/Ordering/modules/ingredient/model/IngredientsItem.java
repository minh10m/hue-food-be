package com.minh.Online.Food.Ordering.modules.ingredient.model;

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
public class IngredientsItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToOne
    private IngredientCategory category;

    @ManyToOne
    @JsonIgnore
    private Restaurant restaurant;

    private boolean inStoke = true;

}
