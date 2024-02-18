package com.example.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.dtos.CategoryCreateDTO;
import com.example.dtos.CategoryItemDTO;
import com.example.entities.CategoryEntity;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mapping(target = "image", ignore = true)
    CategoryEntity categoryCreateDTO(CategoryCreateDTO model);

    @Mapping(target = "dateCreated", source = "creationTime", dateFormat = "dd.MM.yyyy HH.MM.SS")
    CategoryItemDTO categoryItemDTO(CategoryEntity category);
}