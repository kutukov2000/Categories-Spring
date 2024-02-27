package com.example.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entities.ProductEntity;
import com.example.repositories.ProductRepository;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("api/products")
public class ProductController {
    private final ProductRepository productRepository;

    @GetMapping
    public ResponseEntity<List<ProductEntity>> index() {
        List<ProductEntity> list = productRepository.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
