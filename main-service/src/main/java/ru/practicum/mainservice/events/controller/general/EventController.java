package ru.practicum.mainservice.events.controller.general;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.events.dto.EventSearchParams;
import ru.practicum.mainservice.events.dto.EventShortDto;
import ru.practicum.mainservice.events.service.EventService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/events")
public class EventController {
    private final EventService eventService;

    @GetMapping()
    public List<EventShortDto> findEventsAll(@Valid @ModelAttribute EventSearchParams params) {
        return eventService.findEventsAll(params);
    }
}
