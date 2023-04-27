package com.example.pizza.controller;

import com.example.pizza.dto.OrderDtoPriceRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/*
 * Контроллер - корзина с товарами
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("pizza-project/cart/")
public class SessionController {
    private final HttpSession httpSession;
    public static String CART = "cart";
    private final ObjectMapper objectMapper;

    @PostMapping("/add-session-atr")
    public void addAttributeInSession(@RequestBody OrderDtoPriceRequest orderDtoPriceRequest) throws JsonProcessingException {
        List<OrderDtoPriceRequest> orderDtoPriceRequestList;

        if (httpSession.getAttribute(CART) == null) {
            orderDtoPriceRequestList = new ArrayList<>();
        } else {
            String strObject = objectMapper.writeValueAsString(httpSession.getAttribute(CART));
            OrderDtoPriceRequest[] arr = objectMapper.readValue(strObject, OrderDtoPriceRequest[].class);
            orderDtoPriceRequestList = Arrays.stream(arr).collect(Collectors.toList());
        }
        orderDtoPriceRequestList.add(orderDtoPriceRequest);
        httpSession.setAttribute(CART, orderDtoPriceRequestList);
    }

    @GetMapping
    public void deleteAttributeInSession(@RequestParam Long id) throws JsonProcessingException {
        List<OrderDtoPriceRequest> orderDtoPriceRequestList;

        if (httpSession.getAttribute(CART) == null) {
            orderDtoPriceRequestList = new ArrayList<>();
        } else {
            String strObject = objectMapper.writeValueAsString(httpSession.getAttribute(CART));
            OrderDtoPriceRequest[] arr = objectMapper.readValue(strObject, OrderDtoPriceRequest[].class);
            orderDtoPriceRequestList = Arrays.stream(arr).collect(Collectors.toList());
        }
        orderDtoPriceRequestList.remove(id.intValue());
        httpSession.setAttribute(CART, orderDtoPriceRequestList);
    }


    @GetMapping("/get-cart")
    public ResponseEntity<?> getCart() {
        return new ResponseEntity<>(httpSession.getAttribute(CART), HttpStatus.OK);
    }

    @DeleteMapping("/clear")
    @ResponseStatus(HttpStatus.OK)
    public void clear() {
        httpSession.removeAttribute(CART);
    }
}
