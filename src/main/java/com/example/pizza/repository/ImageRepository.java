package com.example.pizza.repository;

import com.example.pizza.model.Image;
import com.example.pizza.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {
}