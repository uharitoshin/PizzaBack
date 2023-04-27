package com.example.pizza.dto;

import com.example.pizza.model.Ingredient;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class PizzaResponseDto {

    String name;
    BigDecimal price;
    LocalDate startDate;
    LocalDate endDate;
    Integer expired;
    Boolean enabled;
    List<Ingredient> ingredients;
}
