package com.example.pizza.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class PizzaSaveOrUpdateRequestDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private List<String> ingredients;
    private LocalDate endDate;
    private Integer expired;
}
