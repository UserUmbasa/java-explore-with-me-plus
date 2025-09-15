package ru.practicum.mainservice.category.controller.admin;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.Exception.validation.Marker;
import ru.practicum.mainservice.category.dto.CategoryDto;
import ru.practicum.mainservice.category.service.CategoryService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/categories")
public class CategoryAdminController {
    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public CategoryDto addCategory(@Validated(Marker.OnUpdate.class) @RequestBody CategoryDto category) {
        return categoryService.saveCategory(category);
    }

    @PatchMapping("/{catId}")
    @Transactional
    public CategoryDto updateCategory(@PathVariable Long catId, @RequestBody CategoryDto category) {
        return categoryService.update(catId, category);
    }
}
