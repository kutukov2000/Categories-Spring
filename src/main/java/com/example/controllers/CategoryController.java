package com.example.controllers;

import lombok.AllArgsConstructor;

import com.example.dtos.CategoryCreateDTO;
import com.example.dtos.CategoryEditDTO;
import com.example.dtos.CategoryItemDTO;
import com.example.entities.CategoryEntity;
import com.example.mapper.CategoryMapper;
import com.example.repositories.CategoryRepository;
import com.example.storage.FileSaveFormat;
import com.example.storage.StorageService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
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

    @GetMapping("{id}")
    public ResponseEntity<CategoryItemDTO> getById(@PathVariable int id) {
        return categoryRepository.findById(id)
                .map(category -> new ResponseEntity<>(categoryMapper.categoryItemDTO(category), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
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

    @PutMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CategoryItemDTO> edit(@ModelAttribute CategoryEditDTO editedCategory) {
        var existedCategory = categoryRepository.findById(editedCategory.getId()).orElse(null);
        if (existedCategory == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        var entity = categoryMapper.categoryEditDto(editedCategory);
        if (editedCategory.getFile() == null)
            entity.setImage(existedCategory.getImage());
        else {
            try {
                storageService.deleteImage(existedCategory.getImage());
                String fileName = storageService.saveImage(editedCategory.getFile(), FileSaveFormat.WEBP);
                entity.setImage(fileName);
            } catch (Exception exception) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

        entity.setCreationTime(existedCategory.getCreationTime());

        categoryRepository.save(entity);
        var result = categoryMapper.categoryItemDTO(entity);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteCategory(@PathVariable int id) {
        return categoryRepository.findById(id)
                .map(category -> {
                    try {
                        storageService.deleteImage(category.getImage());
                        categoryRepository.delete(category);
                        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
                    } catch (IOException ex) {
                        return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
                    }
                })
                .orElseGet(() -> {
                    return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
                });
    }

    @GetMapping("/search")
    public ResponseEntity<Page<CategoryItemDTO>> searchByName(@RequestParam(required = false) String name,
            Pageable pageable) {
        Page<CategoryEntity> categories = categoryRepository.findByNameContainingIgnoreCase(name, pageable);
        Page<CategoryItemDTO> result = categories.map(categoryMapper::categoryItemDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}