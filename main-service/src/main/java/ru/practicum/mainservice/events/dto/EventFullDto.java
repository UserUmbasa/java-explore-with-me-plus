package ru.practicum.mainservice.events.dto;
import lombok.Data;
import ru.practicum.mainservice.events.model.Location;
import ru.practicum.mainservice.events.model.State;

import java.time.LocalDateTime;

@Data
public class EventFullDto {
    private Long id;
    private Long confirmedRequests;
    private LocalDateTime createdOn;
    private String description;
    private LocalDateTime eventDate;
    private Long initiator;
    private Location location;
    private Boolean paid;
    private Long participantLimit;
    private LocalDateTime publishedOn;
    private Boolean requestModeration;
    private State state;
    private String title;
    private Long views;
}
