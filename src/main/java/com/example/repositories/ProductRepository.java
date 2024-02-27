package com.example.repositories;

import com.example.entities.ProductEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
    Page<ProductEntity> findByNameContainingIgnoreCase(String name, Pageable pageable);
}