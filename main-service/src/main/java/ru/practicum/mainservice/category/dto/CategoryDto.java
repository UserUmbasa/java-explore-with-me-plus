package ru.practicum.mainservice.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.practicum.mainservice.Exception.validation.Marker;

@Data
public class CategoryDto {
    private Long id;
    @NotBlank(groups = Marker.OnCreate.class, message = "имя не должен содержать пустым или null")
    @Size(groups = Marker.OnCreate.class, min = 1, max = 50, message = "Длина имени должна быть от 1 до 200 символов")
    @Size(groups = Marker.OnUpdate.class, min = 1, max = 50, message = "Длина имени должна быть от 1 до 200 символов")
    private String name;
}
