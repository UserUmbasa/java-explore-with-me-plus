package ru.practicum.mainservice.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mainservice.exception.DuplicateException;
import ru.practicum.mainservice.exception.NotFoundException;
import ru.practicum.mainservice.category.dto.CategoryDto;
import ru.practicum.mainservice.category.model.Category;
import ru.practicum.mainservice.category.repository.CategoryRepository;
import ru.practicum.mainservice.mapper.DtoModelMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final DtoModelMapper dtoModelMapper;

    @Override
    @Transactional
    public CategoryDto saveCategory(CategoryDto category) {
        if (categoryRepository.existsByName(category.getName())){
            throw new DuplicateException("такая категория уже есть");
        }
        Category result = categoryRepository.save(dtoModelMapper.mapToCategory(category));
        return dtoModelMapper.mapToCategoryDto(result);
    }

    @Override
    @Transactional
    public CategoryDto update(Long catId, CategoryDto category) {
        if (categoryRepository.existsByName(category.getName())){
            throw new DuplicateException("такая категория уже есть");
        }
        Category result = categoryRepository.findById(catId).orElseThrow(() -> new NotFoundException("такой категории нет"));
        result.setName(category.getName());
        return dtoModelMapper.mapToCategoryDto(result);
    }

    @Override
    public CategoryDto findCategory(Long catId) {
        Category result = categoryRepository.findById(catId).orElseThrow(() -> new NotFoundException("такой категории нет"));
        return dtoModelMapper.mapToCategoryDto(result);
    }

    @Override
    public List<CategoryDto> findAllCategory(Long from, Long size) {
        Pageable pageable = PageRequest.of(
                from.intValue(),
                size.intValue()
        );
        List<Category> result = categoryRepository.findAll(pageable).getContent();
        return result.stream()
                .map(dtoModelMapper::mapToCategoryDto)
                .toList();
    }

    @Transactional
    public void delete(Long catId) {
        if (!categoryRepository.existsById(catId)) {
            throw new NotFoundException("такого пользователя нет");
        }
        categoryRepository.deleteById(catId);
    }
}
