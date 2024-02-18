package com.example;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.example.entities.CategoryEntity;
import com.example.repositories.CategoryRepository;
import com.example.storage.StorageProperties;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    CommandLineRunner runner(CategoryRepository categoryRepository) {
        return args -> {

            // CategoryEntity category = new CategoryEntity();
            // category.setName("Продукти");
            // category.setDescription("Для усіх людей");
            // category.setImage("product.jpg");
            // category.setCreationTime(LocalDateTime.now());

            // categoryRepository.save(category);
        };
    }
}
