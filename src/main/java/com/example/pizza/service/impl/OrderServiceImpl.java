package com.example.pizza.service.impl;

import com.example.pizza.dto.OrderDto;
import com.example.pizza.dto.OrderDtoPriceRequest;
import com.example.pizza.mapstruct.OrderMapper;
import com.example.pizza.model.Orders;
import com.example.pizza.model.Pizza;
import com.example.pizza.repository.OrderRepository;
import com.example.pizza.repository.PizzaRepository;
import com.example.pizza.service.OrderService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Data
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final PizzaRepository pizzaRepository;
    private final OrderMapper orderMapper;

    @Value("${application.scale}")
    public Integer SCALE;

    @Override
    public void orderSave(OrderDto orderDto) {
        Orders order = orderMapper.toModel(orderDto);
        orderRepository.save(order);
    }

    public OrderDto orderPriceAccepted(OrderDtoPriceRequest dto) {

        List<OrderDto.OrderDtoInnerRequestDto> orderDtoInner = new ArrayList<>();
        Map<String, Pizza> pizzaMap = getPizzaMapForOrderDtoPriceRequest(dto);

        dto.getOrderDtoInnerRequestDtos().forEach(orderDtoInnerPriceRequestDto -> {
            Pizza pizza = pizzaMap.get(orderDtoInnerPriceRequestDto.getProductName());

            if (pizza != null) {

                OrderDto.OrderDtoInnerRequestDto innerDto = orderMapper.setDefaultValue(orderDtoInnerPriceRequestDto);

                BigDecimal price = pizza.getPrice().multiply(
                        BigDecimal.valueOf(orderDtoInnerPriceRequestDto.getCount()),
                        new MathContext(SCALE, RoundingMode.HALF_UP)
                );

                innerDto.setPrice(price);
                orderDtoInner.add(innerDto);
            }
        });

        return orderMapper.paramsToOrderDto(dto.getNameForYour(), orderDtoInner, getAllCountForOrder(orderDtoInner));
    }

    private BigDecimal getAllCountForOrder(List<OrderDto.OrderDtoInnerRequestDto> orderDtoInner) {
        return orderDtoInner.stream().map(OrderDto.OrderDtoInnerRequestDto::getPrice)
                .reduce(BigDecimal::add).get();
    }

    private Map<String, Pizza> getPizzaMapForOrderDtoPriceRequest(OrderDtoPriceRequest dto) {
        return pizzaRepository.findAllByNameIn(
                        dto.getOrderDtoInnerRequestDtos().stream()
                                .map(OrderDtoPriceRequest.OrderDtoInnerPriceRequestDto::getProductName)
                                .toList()
                ).stream()
                .collect(
                        Collectors.toMap(
                                Pizza::getName,
                                Function.identity())
                );
    }

    public List<OrderDto> getAllOrders() {
        return orderMapper.toDtos(orderRepository.findAll());
    }
}
