package ru.practicum.mainservice.events.controller.general;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.events.service.EventService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/events")
public class EventController {
    private final EventService eventService;


}
