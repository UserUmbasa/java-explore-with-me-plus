package ru.practicum.mainservice.event.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.EndpointHitDTO;
import ru.practicum.mainservice.event.dto.EventDtoOut;
import ru.practicum.mainservice.event.dto.EventShortDtoOut;
import ru.practicum.mainservice.event.model.EventFilter;
import ru.practicum.mainservice.event.model.EventState;
import ru.practicum.mainservice.event.service.EventService;
import ru.practicum.mainservice.exception.InvalidRequestException;
import ru.practicum.statsclient.client.StatsClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;

import static ru.practicum.mainservice.constants.Constants.DATE_TIME_FORMAT;


@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class PublicEventController {

    private final EventService eventService;
    private final StatsClient statsClient;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    // Получение событий с возможностью фильтрации
    @GetMapping
    public Collection<EventShortDtoOut> getEvents(
            @Size(min = 3, max = 1000, message = "Текст должен быть длиной от 3 до 1000 символов")
            @RequestParam(required = false) String text,
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(required = false) Boolean paid,
            @RequestParam(required = false) @DateTimeFormat(pattern = DATE_TIME_FORMAT) LocalDateTime rangeStart,
            @RequestParam(required = false) @DateTimeFormat(pattern = DATE_TIME_FORMAT) LocalDateTime rangeEnd,
            @RequestParam(defaultValue = "false") Boolean onlyAvailable,
            @RequestParam(defaultValue = "EVENT_DATE") String sort,
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size,
            HttpServletRequest request) {

        EventFilter filter = EventFilter.builder()
                .text(text)
                .categories(categories)
                .paid(paid)
                .rangeStart(rangeStart)
                .rangeEnd(rangeEnd)
                .onlyAvailable(onlyAvailable)
                .sort(sort)
                .from(from)
                .size(size)
                .state(EventState.PUBLISHED)
                .build();

        if (filter.getRangeStart() != null && filter.getRangeEnd() != null
                && filter.getRangeStart().isAfter(filter.getRangeEnd())) {
            throw new InvalidRequestException("The start date of the range must be earlier than the end date.");
        }

        Collection<EventShortDtoOut> events = eventService.findShortEventsBy(filter);

        Collection<Long> ids = events.stream()
                .map(EventShortDtoOut::getId)
                .toList();
        return events;
    }

    @GetMapping("/{eventId}")
    public EventDtoOut get(@PathVariable @Min(1) Long eventId,
                           HttpServletRequest request) {
        log.debug("request for published event id:{}", eventId);

        //статистика
        EndpointHitDTO endpointHitDto = EndpointHitDTO.builder()
                .app("events") // название приложения
                .uri("/events/" + eventId) // URI запроса
                .ip(getClientIp(request)) // получаем IP клиента
                .timestamp(LocalDateTime.now().format(FORMATTER))
                .build();
        statsClient.saveHit(endpointHitDto);
        EventDtoOut dtoOut = eventService.findPublished(eventId);
        return dtoOut;
    }

    //IP-адрес
    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
