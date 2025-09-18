package ru.practicum.mainservice.events.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.events.dto.EventFullDto;
import ru.practicum.mainservice.events.dto.EventShortDto;
import ru.practicum.mainservice.events.dto.NewEventDto;
import ru.practicum.mainservice.events.dto.UpdateEventUser;
import ru.practicum.mainservice.events.service.EventService;
import ru.practicum.mainservice.exception.validation.Marker;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users/{userId}/events")
public class EventUserController {
    private final EventService eventService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventShortDto addEvent(@Validated(Marker.OnCreate.class) @RequestBody NewEventDto event,
                                  @PathVariable Long userId) {
        return eventService.saveEvent(userId, event);
    }

    @GetMapping()
    public List<EventShortDto> findAllEventsByUser(@PathVariable Long userId,
                                                @RequestParam(name = "from", defaultValue = "0") Long from,
                                                @RequestParam(name = "size", defaultValue = "10") Long size) {
        return eventService.findUserEvents(userId, from, size);
    }

    @GetMapping("/{eventId}")
    public EventShortDto findEventByUser(@PathVariable Long userId, @PathVariable Long eventId) {
        return eventService.findUserEvent(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    public EventShortDto updateEvent(@PathVariable Long userId, @PathVariable Long eventId,
                                     @RequestBody UpdateEventUser event) {
        return eventService.updateUserEvent(userId, eventId, event);
    }
}
