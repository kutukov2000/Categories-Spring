package com.example.controllers;

import lombok.AllArgsConstructor;

import com.example.entities.CategoryCreateModel;
import com.example.entities.CategoryEntity;
import com.example.repositories.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/categories")
public class CategoryController {
    private final CategoryRepository categoryRepository;

    @GetMapping
    public ResponseEntity<List<CategoryEntity>> index() {
        List<CategoryEntity> list = categoryRepository.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping
    CategoryEntity createCategory(@RequestBody CategoryCreateModel newCategory){
        CategoryEntity category=new CategoryEntity();
        category.setName(newCategory.getName());
        category.setDescription(newCategory.getDescription());
        category.setImage(newCategory.getImage());
        category.setCreationTime(LocalDateTime.now());
        return categoryRepository.save(category);
    }
}