package ru.practicum.mainservice.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.practicum.mainservice.Exception.validation.CustomEmail;
import ru.practicum.mainservice.Exception.validation.Marker;

@Data
public class UserDto {
    private Long id;
    @NotBlank(groups = Marker.OnCreate.class, message = "имя не должен содержать пустым или null")
    @Size(groups = Marker.OnCreate.class, min = 2, max = 250, message = "Длина имени должна быть от 2 до 250 символов")
    private String name;

    @CustomEmail(groups = Marker.OnCreate.class)
    @NotBlank(groups = Marker.OnCreate.class, message = "Email не может быть пустым или null")
//    @Email(groups = Marker.OnUpdate.class, message = "Неверный формат email")
//    @Pattern(groups = Marker.OnUpdate.class, regexp = "(.+)|null", message = "Неверный формат email")
    private String email;
}
