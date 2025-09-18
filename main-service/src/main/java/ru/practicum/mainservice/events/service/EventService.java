package ru.practicum.mainservice.events.service;

import ru.practicum.mainservice.events.dto.EventShortDto;
import ru.practicum.mainservice.events.dto.NewEventDto;

public interface EventService {
    EventShortDto saveEvent(Long userId, NewEventDto event);
}
