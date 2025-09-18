package ru.practicum.mainservice.events.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.events.dto.EventShortDto;
import ru.practicum.mainservice.events.dto.UpdateEventAdminRequest;
import ru.practicum.mainservice.events.service.EventService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/events")
public class EventAdminController {
    private final EventService eventService;

    @PatchMapping("/{eventId}")
    public EventShortDto updateAdminEvent(@PathVariable Long eventId, @RequestBody UpdateEventAdminRequest event) {
        return eventService.updateAdminEvent(eventId, event);
    }
}
