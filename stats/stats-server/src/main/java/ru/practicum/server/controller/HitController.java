package ru.practicum.server.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.EndpointHitDTO;
import ru.practicum.dto.ViewStatsDTO;
import ru.practicum.server.service.HitService;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
public class HitController {

    private final HitService hitService;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public void createHit(@Valid @RequestBody EndpointHitDTO endpointHitDTO) {
        log.info("Received hit: {}", endpointHitDTO);
        hitService.createHit(endpointHitDTO);
    }

    @GetMapping("/stats")
    public List<ViewStatsDTO> getStats(
            @RequestParam String start,
            @RequestParam String end,
            @RequestParam(required = false) String uris, // Изменено на String
            @RequestParam(defaultValue = "false") Boolean unique) {

        log.info("RAW PARAMS - start: '{}', end: '{}', uris: {}, unique: {}", start, end, uris, unique);

        try {
            // Декодируем параметры дат
            String decodedStart = URLDecoder.decode(start, StandardCharsets.UTF_8);
            String decodedEnd = URLDecoder.decode(end, StandardCharsets.UTF_8);

            log.info("DECODED - start: '{}', end: '{}'", decodedStart, decodedEnd);

            LocalDateTime startDate = LocalDateTime.parse(decodedStart, FORMATTER);
            LocalDateTime endDate = LocalDateTime.parse(decodedEnd, FORMATTER);

            log.info("PARSED - start: {}, end: {}", startDate, endDate);

            // Преобразуем строку uris в список
            List<String> uriList = null;
            if (uris != null && !uris.trim().isEmpty()) {
                uriList = Arrays.stream(uris.split(","))
                        .map(String::trim)
                        .filter(uri -> !uri.isEmpty())
                        .collect(Collectors.toList());
                log.info("Processed URIs list: {}", uriList);
            }

            List<ViewStatsDTO> result = hitService.getStats(startDate, endDate, uriList, unique);
            log.info("FINAL RESULT: {}", result);

            return result;
        } catch (Exception e) {
            log.error("ERROR parsing dates: start='{}', end='{}', error: {}", start, end, e.getMessage());
            throw new IllegalArgumentException("Неверный формат даты. Используйте 'yyyy-MM-dd HH:mm:ss'");
        }
    }
}