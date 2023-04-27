package com.example.pizza.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "images")
@Entity
@FieldDefaults(level = PRIVATE)
public class Image {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;

    byte[] image;

    @ManyToOne
    @JoinColumn(name = "pizza_id")
    @JsonIgnore
    Pizza pizza;
}
