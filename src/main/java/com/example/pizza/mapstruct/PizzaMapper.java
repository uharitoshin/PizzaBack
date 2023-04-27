package com.example.pizza.mapstruct;

import com.example.pizza.dto.PizzaResponseDto;
import com.example.pizza.dto.PizzaSaveOrUpdateRequestDto;
import com.example.pizza.model.Ingredient;
import com.example.pizza.model.Pizza;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface PizzaMapper {

    PizzaResponseDto toDto(Pizza pizza);

    List<PizzaResponseDto> toDtos(List<Pizza> pizza);

    @AfterMapping
    default void afterMappingPizza(@MappingTarget Pizza pizza) {
        pizza.getIngredients().forEach(item -> item.setPizza(pizza));
    }

    @Mapping(target = "startDate", expression = "java(java.time.LocalDate.now())")
    @Mapping(target = "enabled", constant = "true")
    @Mapping(target = "ingredients", qualifiedByName = "mappingIngredients")
    Pizza fromDto(PizzaSaveOrUpdateRequestDto dto);

    @Mapping(target = "ingredients", ignore = true)
    Pizza updateModel(@MappingTarget Pizza pizza, PizzaSaveOrUpdateRequestDto dto);

    @Named("mappingIngredients")
    default List<Ingredient> toIngredients(List<String> dtoIngredient) {

        List<Ingredient> ingredientsInModelPizza = new ArrayList<>();

        dtoIngredient.forEach(ingredientDto -> {
            Ingredient ingredient = new Ingredient();
            ingredient.setEnabled(true);
            ingredient.setIngredient(ingredientDto);
            ingredientsInModelPizza.add(ingredient);
        });

        return ingredientsInModelPizza;
    }

    List<Pizza> fromDtos(List<PizzaSaveOrUpdateRequestDto> dtos);

}
