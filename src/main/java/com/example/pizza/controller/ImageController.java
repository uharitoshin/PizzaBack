package com.example.pizza.controller;

import com.example.pizza.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("pizza-project/images")
public class ImageController {

    private final ImageService imageService;


    @PostMapping(path = "/save",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> saveImagePizza(@RequestParam MultipartFile document,
                                            @RequestParam String nameProduct) throws IOException {

        return new ResponseEntity<>(imageService.save(document, nameProduct));
    }

    @GetMapping(path = "/get/all")
    public ResponseEntity<?> getImagePizza() {

        return new ResponseEntity<>(imageService.getAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/get/{pizza-name}",
            produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<?> getImageByPizzaName(@PathVariable("pizza-name") String name) {

        return new ResponseEntity<>(imageService.getImageByPizzaName(name), HttpStatus.OK);
    }

    @PutMapping(path = "/put/{pizza-name}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void putImageByPizzaName(@PathVariable("pizza-name") String name,
                                    @RequestParam MultipartFile document,
                                    @RequestParam Integer indexDocument) {

        imageService.putImageByPizzaName(document, name, indexDocument);
    }
}