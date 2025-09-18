package ru.practicum.mainservice.category.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mainservice.category.dto.CategoryDto;
import ru.practicum.mainservice.category.dto.CategoryDtoOut;
import ru.practicum.mainservice.category.mapper.CategoryMapper;
import ru.practicum.mainservice.category.model.Category;
import ru.practicum.mainservice.category.repository.CategoryRepository;
import ru.practicum.mainservice.event.repository.EventRepository;
import ru.practicum.mainservice.exception.NotFoundException;

import java.util.Collection;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    public final EventRepository eventRepository;

    @Override
    public Collection<CategoryDtoOut> getAll(Integer offset, Integer limit) {
        Collection<Category> categories = categoryRepository.findWithOffsetAndLimit(offset, limit);
        return categories.stream()
                .map(CategoryMapper::toDto)
                .toList();
    }

    @Override
    public CategoryDtoOut get(Long id) {
        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Category", id));

        return CategoryMapper.toDto(category);
    }

    @Override
    @Transactional
    public CategoryDtoOut add(CategoryDto categoryDto) {
        Category category = CategoryMapper.fromDto(categoryDto);
        Category saved = categoryRepository.save(category);
        return CategoryMapper.toDto(saved);
    }

    @Override
    @Transactional
    public CategoryDtoOut update(Long id, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category", id));

        category.setName(categoryDto.getName());
        Category saved = categoryRepository.save(category);
        return CategoryMapper.toDto(saved);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new NotFoundException("Category", id);
        }

        if (eventRepository.existsByCategoryId(id)) {
            throw new IllegalStateException("Cannot delete category. There are events associated with it.");
        }

        categoryRepository.deleteById(id);
    }
}
