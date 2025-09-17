package ru.practicum.mainservice.events.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.events.dto.NewEventDto;
import ru.practicum.mainservice.events.service.EventService;
import ru.practicum.mainservice.exception.validation.Marker;
import ru.practicum.mainservice.user.dto.UserDto;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users/{userId}/events")
public class EventUserController {
    private final EventService eventService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto addEvent(@Validated(Marker.OnCreate.class) @RequestBody NewEventDto event,
                            @PathVariable Long userId) {
        return eventService.saveEvent(userId, event);
    }
}
