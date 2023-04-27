package com.example.pizza.service;

import com.example.pizza.model.Image;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    HttpStatus save(MultipartFile document, String nameProduct);

    List<Image> getAll();

    byte[] getImageByPizzaName(String pizzaName);

    void putImageByPizzaName(MultipartFile document, String pizzaName, Integer indexDocument);
}
