package ru.practicum.mainservice.category.service;

import ru.practicum.mainservice.category.dto.CategoryDto;

public interface CategoryService {
    CategoryDto saveCategory(CategoryDto category);

    CategoryDto update(Long catId, CategoryDto category);

    CategoryDto findCategory(Long catId);
}
