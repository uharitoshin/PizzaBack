package com.example.pizza.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class OrderDtoPriceRequest {

    String nameForYour;
    List<OrderDtoInnerPriceRequestDto> orderDtoInnerRequestDtos;

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class OrderDtoInnerPriceRequestDto {
        String productName;
        Integer count;
    }
}
