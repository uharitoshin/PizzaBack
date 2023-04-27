package com.example.pizza.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table
@Entity
@FieldDefaults(level = PRIVATE)
public class Pizza {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;

    String name;

    BigDecimal price;

    LocalDate startDate;

    LocalDate endDate;

    Integer expired;

    Boolean enabled;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pizza")
    @ToString.Exclude
    @JsonIgnore
    List<Ingredient> ingredients;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pizza")
    @ToString.Exclude
    @JsonIgnore
    List<Image> images;

    public void addIngredient(Ingredient ingredient) {
        if (ingredients == null) ingredients = new ArrayList<>();
        ingredients.add(ingredient);
        assert ingredient != null;
        ingredient.setPizza(this);
    }

    public void addImages(Image image) {
        if (images == null) images = new ArrayList<>();
        images.add(image);
        assert image != null;
        image.setPizza(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Pizza pizza = (Pizza) o;
        return id != null && Objects.equals(id, pizza.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
