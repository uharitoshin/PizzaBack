package com.example.pizza.service;

import com.example.pizza.dto.OrderDto;
import com.example.pizza.dto.OrderDtoPriceRequest;

import java.util.List;

public interface OrderService {
    void orderSave(OrderDto orderDto);

    List<OrderDto> getAllOrders();

    OrderDto orderPriceAccepted(OrderDtoPriceRequest dto);
}
