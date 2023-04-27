package com.example.pizza.service.impl;

import com.example.pizza.model.Image;
import com.example.pizza.model.Pizza;
import com.example.pizza.repository.ImageRepository;
import com.example.pizza.repository.PizzaRepository;
import com.example.pizza.service.ImageService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Data
@RequiredArgsConstructor
@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final PizzaRepository pizzaRepository;

    @Override
    @SneakyThrows
    @Transactional
    public HttpStatus save(MultipartFile document, String nameProduct) {
        Pizza pizza = pizzaRepository.findAllByName(nameProduct);

        if (pizza == null) return HttpStatus.BAD_REQUEST;

        Image image = new Image();
        image.setImage(document.getBytes());
        image.setPizza(pizza);

        imageRepository.save(image);
        return HttpStatus.OK;
    }

    @Override
    public List<Image> getAll() {
        var allImage = imageRepository.findAll();
        log.info("all image pizza : {}", allImage.size());
        return allImage;
    }

    @Override
    public byte[] getImageByPizzaName(String pizzaName) {
        Pizza pizza = pizzaRepository.findAllByName(pizzaName);
        if (pizza == null) return null;
        return pizza.getImages().get(0).getImage();
    }

    @Override
    @SneakyThrows
    @Transactional
    public void putImageByPizzaName(MultipartFile document, String pizzaName, Integer indexDocument) {
        Pizza pizza = pizzaRepository.findAllByName(pizzaName);
        if (pizza == null) return;
        Image image = pizza.getImages().get(indexDocument);
        if (image == null) return;
        image.setImage(document.getBytes());
        imageRepository.save(image);
    }

}
