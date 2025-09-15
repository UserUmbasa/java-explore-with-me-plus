package ru.practicum.mainservice.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mainservice.Exception.NotFoundException;
import ru.practicum.mainservice.category.dto.CategoryDto;
import ru.practicum.mainservice.category.model.Category;
import ru.practicum.mainservice.category.repository.CategoryRepository;
import ru.practicum.mainservice.mapper.DtoModelMapper;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final DtoModelMapper dtoModelMapper;

    @Override
    public CategoryDto saveCategory(CategoryDto category) {
        Category result = categoryRepository.save(dtoModelMapper.mapToCategory(category));
        return dtoModelMapper.mapToCategoryDto(result);
    }

    @Override
    public CategoryDto update(Long catId, CategoryDto category) {
        Category result = categoryRepository.findById(catId).orElseThrow(() -> new NotFoundException("такой категории нет"));
        result.setName(category.getName());
        return dtoModelMapper.mapToCategoryDto(result);
    }

    @Override
    public CategoryDto findCategory(Long catId) {
        Category result = categoryRepository.findById(catId).orElseThrow(() -> new NotFoundException("такой категории нет"));
        return dtoModelMapper.mapToCategoryDto(result);
    }
}
