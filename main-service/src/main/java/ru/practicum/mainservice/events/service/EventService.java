package ru.practicum.mainservice.events.service;

import jakarta.validation.Valid;
import ru.practicum.mainservice.events.dto.*;

import java.util.List;

public interface EventService {
    EventShortDto saveEvent(Long userId, NewEventDto event);

    List<EventShortDto> findEventsAll(EventSearchParams params);

    EventShortDto updateAdminEvent(Long eventId, UpdateEventAdminRequest event);

    List<EventShortDto> findUserEvents(Long userId, Long from, Long size);

    EventShortDto findUserEvent(Long userId, Long eventId);

    EventShortDto updateUserEvent(Long userId, Long eventId, UpdateEventUser event);
}
