package ru.practicum.mainservice.events.service;

import ru.practicum.mainservice.events.dto.EventFullDto;
import ru.practicum.mainservice.events.dto.NewEventDto;

public interface EventService {
    EventFullDto saveEvent(Long userId, NewEventDto event);
}
