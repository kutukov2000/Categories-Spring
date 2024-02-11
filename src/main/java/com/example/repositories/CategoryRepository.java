package com.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entities.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer>{
}
