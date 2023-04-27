package com.example.pizza.mapstruct;

import com.example.pizza.dto.OrderDto;
import com.example.pizza.dto.OrderDtoPriceRequest;
import com.example.pizza.model.OrderInner;
import com.example.pizza.model.Orders;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.math.BigDecimal;
import java.util.List;


@Mapper
public interface OrderMapper {

    @Mapping(target = "time", expression = "java(java.time.LocalDateTime.now())")
    OrderDto paramsToOrderDto(String nameForYour,
                              List<OrderDto.OrderDtoInnerRequestDto> orderDtoInnerRequestDtos,
                              BigDecimal allPrice);

    OrderDto.OrderDtoInnerRequestDto setDefaultValue(OrderDtoPriceRequest.OrderDtoInnerPriceRequestDto dto);


    List<OrderDto> toDtos(List<Orders> orders);

    @Mapping(target = "orderDtoInnerRequestDtos", source = "orderInnerList")
    OrderDto toDto(Orders orders);

    @Mapping(target = "enabled", constant = "true")
    @Mapping(target = "orderInnerList", source = "orderDtoInnerRequestDtos")
    Orders toModel(OrderDto orderDto);

    List<OrderInner> toInnerModels(List<OrderDto.OrderDtoInnerRequestDto> orderDtoInnerRequestDtos);

    @Mapping(target = "enabled", constant = "true")
    OrderInner toInnerModel(OrderDto.OrderDtoInnerRequestDto dtoInner);

    @AfterMapping
    default void afterMappingOrders(@MappingTarget Orders order) {
        order.getOrderInnerList().forEach(orderInner -> {
            orderInner.setOrders(order);
        });
    }
}
