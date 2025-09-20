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
            @RequestParam(required = false) String uris,
            @RequestParam(defaultValue = "false") Boolean unique) {

        log.info("RAW PARAMS - start: '{}', end: '{}', uris: {}, unique: {}", start, end, uris, unique);

        try {
            String decodedStart = URLDecoder.decode(start, StandardCharsets.UTF_8);
            String decodedEnd = URLDecoder.decode(end, StandardCharsets.UTF_8);

            log.info("DECODED - start: '{}', end: '{}'", decodedStart, decodedEnd);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime startDate = LocalDateTime.parse(decodedStart, formatter);
            LocalDateTime endDate = LocalDateTime.parse(decodedEnd, formatter);

            List<String> urisList = null;
            if (uris != null && !uris.isEmpty()) {
                urisList = Arrays.asList(uris.split(","));
            }

            List<ViewStatsDTO> result = hitService.getStats(startDate, endDate, urisList, unique);
            log.info("FINAL RESULT: {}", result);

            return result;
        } catch (Exception e) {
            log.error("ERROR parsing dates: start='{}', end='{}', error: {}", start, end, e.getMessage());
            throw new IllegalArgumentException("Неверный формат даты. Используйте 'yyyy-MM-dd HH:mm:ss'");
        }
    }
}