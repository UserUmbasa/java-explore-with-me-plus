package ru.practicum.mainservice.event.service;

import ru.practicum.mainservice.event.dto.*;
import ru.practicum.mainservice.event.model.EventAdminFilter;
import ru.practicum.mainservice.event.model.EventFilter;

import java.util.Collection;

public interface EventService {

    EventDtoOut add(Long userId, EventCreateDto eventDto);

    EventDtoOut update(Long userId, Long eventId, EventUpdateDto updateRequest);

    EventDtoOut update(Long eventId, EventUpdateAdminDto eventDto);

    EventDtoOut findPublished(Long eventId);

    EventDtoOut find(Long userId, Long eventId);

    Collection<EventShortDtoOut> findShortEventsBy(EventFilter filter);

    Collection<EventDtoOut> findFullEventsBy(EventAdminFilter filter);

    Collection<EventShortDtoOut> findByInitiator(Long userId, Integer offset, Integer limit);
}
