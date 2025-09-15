package ru.practicum.mainservice.category.service;

import ru.practicum.mainservice.category.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto saveCategory(CategoryDto category);

    CategoryDto update(Long catId, CategoryDto category);

    CategoryDto findCategory(Long catId);

    List<CategoryDto> findAllCategory(Long from, Long size);

    void delete(Long catId);
}
