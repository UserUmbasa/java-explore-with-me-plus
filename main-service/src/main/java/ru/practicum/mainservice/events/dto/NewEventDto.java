package ru.practicum.mainservice.events.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NewEventDto {
    private String annotation;
    private Long category;
    private String description;
    private LocalDateTime eventDate;
    private String eventType;
    private Location location;
    private Boolean paid;
    private Long participantLimit;
    private Boolean requestModeration;
    private String title;
}
