package com.example.controllers;

import lombok.AllArgsConstructor;

import com.example.dtos.CategoryCreateDTO;
import com.example.dtos.CategoryItemDTO;
import com.example.entities.CategoryEntity;
import com.example.mapper.CategoryMapper;
import com.example.repositories.CategoryRepository;
import com.example.storage.FileSaveFormat;
import com.example.storage.StorageService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/categories")
public class CategoryController {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final StorageService storageService;

    @GetMapping
    public ResponseEntity<List<CategoryEntity>> index() {
        List<CategoryEntity> list = categoryRepository.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<CategoryItemDTO> createCategory(@ModelAttribute CategoryCreateDTO newCategory) {
        try {
            CategoryEntity category = categoryMapper.categoryCreateDTO(newCategory);
            String image = storageService.saveImage(newCategory.getImage(), FileSaveFormat.WEBP);
            category.setImage(image);
            category.setCreationTime(LocalDateTime.now());
            categoryRepository.save(category);
            return new ResponseEntity<>(categoryMapper.categoryItemDTO(category), HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<CategoryEntity> editCategory(@ModelAttribute CategoryCreateDTO editedCategory, @PathVariable int id) {
        categoryRepository.findById(id)
                .map(category -> {
                    try {
                        category.setName(editedCategory.getName());
                        category.setDescription(editedCategory.getDescription());

                        String image = storageService.saveImage(editedCategory.getImage(), FileSaveFormat.WEBP);
                        category.setImage(image);

                        categoryRepository.save(category);
                        return new ResponseEntity<>(categoryMapper.categoryItemDTO(category), HttpStatus.OK);
                    } catch (Exception ex) {
                        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                    }

                })
                .orElseGet(() -> {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                });

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteCategory(@PathVariable int id) {
        return categoryRepository.findById(id)
                .map(category -> {
                    categoryRepository.delete(category);
                    return new ResponseEntity<HttpStatus>(HttpStatus.OK);
                })
                .orElseGet(() -> {
                    return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
                });
    }

}