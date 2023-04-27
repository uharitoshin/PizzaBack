package com.example.pizza.repository;

import com.example.pizza.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface PizzaRepository extends JpaRepository<Pizza, Long> {

    List<Pizza> findAllByNameIn(Collection<String> name);

    Pizza findAllByName(String name);

    @Query(value = "select * from pizza p " +
            "WHERE p.price <= :price", nativeQuery = true)
    List<Pizza> getFilterByParams(String price);

}
