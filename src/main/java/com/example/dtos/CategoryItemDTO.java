package com.example.dtos;

import lombok.Data;

@Data
public class CategoryItemDTO {
    private int id;
    private String name;
    private String image;
    private String description;
    private String dateCreated;
}
