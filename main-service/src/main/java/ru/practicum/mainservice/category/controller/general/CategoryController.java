package ru.practicum.mainservice.category.controller.general;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.category.dto.CategoryDto;
import ru.practicum.mainservice.category.service.CategoryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/{catId}")
    public CategoryDto findCategory(@PathVariable Long catId) {
        return categoryService.findCategory(catId);
    }

    @GetMapping()
    public List<CategoryDto> findAllCategory(@RequestParam(defaultValue = "0") Long from,
                                             @RequestParam(defaultValue = "10") Long size) {
        return categoryService.findAllCategory(from, size);
    }
}
