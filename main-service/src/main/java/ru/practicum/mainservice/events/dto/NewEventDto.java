package ru.practicum.mainservice.events.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.practicum.mainservice.events.model.Location;
import ru.practicum.mainservice.exception.validation.Marker;

import java.time.LocalDateTime;

@Data
public class NewEventDto {

    @NotBlank(groups = Marker.OnCreate.class, message = "annotation не должна быть пустой или null")
    @Size(groups = Marker.OnCreate.class, min = 20, max = 2000, message = "Длина annotation должна быть от 20 до 2000 символов")
    private String annotation;

    private Long category;

    @NotBlank(groups = Marker.OnCreate.class, message = "description не должно быть пустым или null")
    @Size(groups = Marker.OnCreate.class, min = 20, max = 7000, message = "Длина description должна быть от 20 до 7000 символов")
    private String description;

    @NotNull(message = "не должно быть null")
    @Future(message = "нельзя создать в прошлом")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    @NotNull(message = "не должно быть null")
    private Location location;

    @NotNull
    private Boolean paid;

    @NotNull
    private Long participantLimit;

    @NotNull
    private Boolean requestModeration;

    @NotBlank(groups = Marker.OnCreate.class, message = "title не должно быть пустым или null")
    @Size(groups = Marker.OnCreate.class, min = 3, max = 120, message = "Длина title должна быть от 3 до 120 символов")
    private String title;
}
