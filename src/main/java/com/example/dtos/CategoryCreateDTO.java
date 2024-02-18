package com.example.dtos;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class CategoryCreateDTO {
    private String name;
    private MultipartFile image;
    private String description;
}