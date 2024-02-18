package com.example.dtos;

import lombok.Data;

@Data
public class CategoryCreateDTO {
    private String name;
    private String image;
    private String description;
}