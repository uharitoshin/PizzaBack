package com.example.pizza.controller;

import com.example.pizza.dto.PizzaSaveOrUpdateRequestDto;
import com.example.pizza.service.PizzaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("pizza-project")
public class PizzaController {

    private final PizzaService pizzaService;

    /*
     * Получить все пиццы
     */
    @GetMapping("/pizza/all")
    public ResponseEntity<?> getAll(/*@RequestHeader String authorization*/) {
        return new ResponseEntity<>(pizzaService.getAll(), HttpStatus.OK);
    }

    /*
     * Получить пиццу по id
     */
    @GetMapping("/pizza/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return new ResponseEntity<>(pizzaService.getById(id), HttpStatus.OK);
    }

    /*
     * Получить список отсортированных пицц по переданному параметру
     */
    @GetMapping("/pizza/sort")
    public ResponseEntity<?> getSortByParams(@RequestParam String param) {
        return new ResponseEntity<>(pizzaService.getSortByParams(param), HttpStatus.OK);
    }

    /*
     * Отфильтровать по переданной цене
     */
    @GetMapping("/pizza/filter")
    public ResponseEntity<?> getFilterByParams(@RequestParam String price) {
        return new ResponseEntity<>(pizzaService.getFilterByParams(price), HttpStatus.OK);
    }

    /*
     * Сохранить/Изменить новую пиццу
     */
    @RequestMapping(path = "/modify/save-or-update", method = {RequestMethod.POST, RequestMethod.PUT})
    public ResponseEntity<?> saveOrUpdate(@RequestBody PizzaSaveOrUpdateRequestDto dto) {
        return new ResponseEntity<>(pizzaService.saveOrUpdate(dto), HttpStatus.CREATED);
    }

    /*
     * Сохранить новые пиццы
     */
    @PostMapping("/modify/save-all")
    public ResponseEntity<?> saveAll(@RequestBody List<PizzaSaveOrUpdateRequestDto> dtos) {
        return new ResponseEntity<>(pizzaService.saveAll(dtos), HttpStatus.CREATED);
    }

    /*
     *Удалить пиццу
     */
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/modify/{id}")
    public void deletePizza(@PathVariable Long id) {
        pizzaService.delete(id);
    }
}
