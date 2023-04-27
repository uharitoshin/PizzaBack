package com.example.pizza.service.impl;

import com.example.pizza.dto.PizzaResponseDto;
import com.example.pizza.dto.PizzaSaveOrUpdateRequestDto;
import com.example.pizza.mapstruct.PizzaMapper;
import com.example.pizza.model.Pizza;
import com.example.pizza.repository.PizzaRepository;
import com.example.pizza.service.PizzaService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Data
@RequiredArgsConstructor
@Service
public class PizzaServiceImpl implements PizzaService {
    private final PizzaRepository pizzaRepository;
    private final PizzaMapper pizzaMapper;

    @Transactional
    @Override
    public Pizza saveOrUpdate(PizzaSaveOrUpdateRequestDto dto) {
        Pizza newPizza;
        if (dto.getId() == null) {
            newPizza = pizzaMapper.fromDto(dto);
        } else {
            try {
                Pizza pizza = pizzaRepository.findById(dto.getId()).get();
                newPizza = pizzaMapper.updateModel(pizza, dto);
            } catch (Exception ex) {
                log.error("Pizza by this id: {} is null!", dto.getId());
                throw new RuntimeException("Pizza is null!");
            }
        }
        return pizzaRepository.save(newPizza);
    }

    @Override
    public List<Pizza> saveAll(List<PizzaSaveOrUpdateRequestDto> dtos) {
        List<Pizza> newPizzas = pizzaMapper.fromDtos(dtos);
        return pizzaRepository.saveAll(newPizzas);
    }

    @Override
    public List<PizzaResponseDto> getAll() {
        return pizzaMapper.toDtos(pizzaRepository.findAll());
    }

    @Override
    public PizzaResponseDto getById(Long id) {
        return pizzaMapper.toDto(pizzaRepository.findById(id).get());
    }

    @Override
    public List<PizzaResponseDto> getSortByParams(String param) {
        List<Pizza> pizzas = pizzaRepository.findAll(Sort.by(param));
        return pizzaMapper.toDtos(pizzas);
    }

    @Override
    public List<PizzaResponseDto> getFilterByParams(String price) {
        List<Pizza> pizzas = pizzaRepository.getFilterByParams(price);
        return pizzaMapper.toDtos(pizzas);
    }

    @Override
    public void delete(Long id) {
        pizzaRepository.deleteById(id);
    }
}
