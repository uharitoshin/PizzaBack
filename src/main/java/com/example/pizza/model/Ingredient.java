package com.example.pizza.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE)
@Entity
@Table
public class Ingredient {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "pizza_id")
    @JsonIgnore
    Pizza pizza;

    String ingredient;

    Boolean enabled;
}
