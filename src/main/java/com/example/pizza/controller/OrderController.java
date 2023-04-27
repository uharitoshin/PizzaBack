package com.example.pizza.controller;

import com.example.pizza.dto.OrderDto;
import com.example.pizza.dto.OrderDtoPriceRequest;
import com.example.pizza.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("pizza-project/orders")
public class OrderController {

    private final OrderService orderService;

    /*
     * Получить информацию о стоимости заказа
     * (но еще не сделать заказ)
     */
    @PostMapping("/order-accepted")
    public ResponseEntity<?> orderPriceAccepted(@RequestBody OrderDtoPriceRequest dto) {
        return new ResponseEntity<>(orderService.orderPriceAccepted(dto), HttpStatus.CREATED);
    }

    /*
     * Сохранить заказ (чек заказа)
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/order-save")
    public void orderSave(@RequestBody OrderDto dto) {
        orderService.orderSave(dto);
    }

    /*
     * Получить все заказы - всю историю
     */
    @GetMapping("/order")
    public List<OrderDto> getAllOrder() {
        return orderService.getAllOrders();
    }
}
