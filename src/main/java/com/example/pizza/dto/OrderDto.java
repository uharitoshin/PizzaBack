package com.example.pizza.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class OrderDto {

    String nameForYour;
    BigDecimal allPrice;
    LocalDateTime time;
    List<OrderDtoInnerRequestDto> orderDtoInnerRequestDtos;

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class OrderDtoInnerRequestDto {
        String productName;
        Integer count;
        BigDecimal price;
    }
}
