package com.example.pizza.service;

import com.example.pizza.dto.OrderDto;
import com.example.pizza.dto.OrderDtoPriceRequest;
import com.example.pizza.dto.PizzaResponseDto;
import com.example.pizza.dto.PizzaSaveOrUpdateRequestDto;
import com.example.pizza.model.Pizza;

import java.util.List;

public interface PizzaService {

    Pizza saveOrUpdate(PizzaSaveOrUpdateRequestDto pizzaDto);

    List<Pizza> saveAll(List<PizzaSaveOrUpdateRequestDto> dtos);

    List<PizzaResponseDto> getAll();

    PizzaResponseDto getById(Long id);

    List<PizzaResponseDto> getSortByParams(String param);

    List<PizzaResponseDto> getFilterByParams(String price);

    void delete(Long id);


}
