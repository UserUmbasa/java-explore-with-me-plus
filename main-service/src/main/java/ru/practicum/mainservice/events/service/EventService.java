package ru.practicum.mainservice.events.service;

import ru.practicum.mainservice.events.dto.NewEventDto;

public interface EventService {
    NewEventDto saveEvent(Long userId, NewEventDto event);
}
