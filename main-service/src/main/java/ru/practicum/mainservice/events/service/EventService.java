package ru.practicum.mainservice.events.service;

import ru.practicum.mainservice.events.dto.NewEventDto;
import ru.practicum.mainservice.user.dto.UserDto;

public interface EventService {
    UserDto saveEvent(NewEventDto event);
}
