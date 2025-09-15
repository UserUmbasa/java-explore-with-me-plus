package ru.practicum.mainservice.category.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import ru.practicum.mainservice.Exception.validation.Marker;

@Data
public class CategoryDto {
    private Long id;
    @NotBlank(groups = Marker.OnCreate.class, message = "имя не должен содержать пустым или null")
    private String name;
}
