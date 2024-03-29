package com.example.dtos;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CategoryEditDTO {
    private int id;
    private String name;
    private MultipartFile file;
    private String description;
}
