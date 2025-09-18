package ru.practicum.mainservice.events.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.practicum.mainservice.events.model.Location;
import ru.practicum.mainservice.events.model.State;
import ru.practicum.mainservice.exception.validation.Marker;

import java.time.LocalDateTime;

@Data
public class UpdateEventUser {
    private String annotation;
    private Long category;
    private String description;
//    @Future(message = "нельзя создать в прошлом")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    private Location location;
    private Boolean paid;
    private Long participantLimit;
    private Boolean requestModeration;
    private State stateAction;
    private String title;
}
