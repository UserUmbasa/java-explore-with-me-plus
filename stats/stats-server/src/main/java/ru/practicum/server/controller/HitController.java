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

//    @GetMapping("/stats")
//    public List<ViewStatsDTO> getStats(
//            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
//            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
//            @RequestParam(required = false) List<String> uris,
//            @RequestParam(defaultValue = "false") Boolean unique) {
//
//        log.info("Received stats request: start={}, end={}, uris={}, unique={}", start, end, uris, unique);
//        return hitService.getStats(start, end, uris, unique);
//    }

//    @GetMapping("/stats")
//    public List<ViewStatsDTO> getStats(
//            @RequestParam String start,
//            @RequestParam String end,
//            @RequestParam(required = false) List<String> uris,
//            @RequestParam(defaultValue = "false") Boolean unique) {
//
//        try {
//            LocalDateTime startDate = LocalDateTime.parse(
//                    URLDecoder.decode(start, StandardCharsets.UTF_8), FORMATTER);
//            LocalDateTime endDate = LocalDateTime.parse(
//                    URLDecoder.decode(end, StandardCharsets.UTF_8), FORMATTER);
//
//            log.info("Received stats request: start={}, end={}, uris={}, unique={}", startDate, endDate, uris, unique);
//            return hitService.getStats(startDate, endDate, uris, unique);
//        } catch (Exception e) {
//            throw new IllegalArgumentException("Неверный формат даты. Используйте 'yyyy-MM-dd HH:mm:ss'");
//        }
//    }

    @GetMapping("/stats")
    public List<ViewStatsDTO> getStats(
            @RequestParam String start,
            @RequestParam String end,
            @RequestParam(required = false) List<String> uris,
            @RequestParam(defaultValue = "false") Boolean unique) {

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime startDate = LocalDateTime.parse(
                    URLDecoder.decode(start, StandardCharsets.UTF_8), formatter);
            LocalDateTime endDate = LocalDateTime.parse(
                    URLDecoder.decode(end, StandardCharsets.UTF_8), formatter);

            log.info("Received stats request: start={}, end={}, uris={}, unique={}",
                    startDate, endDate, uris, unique);

            return hitService.getStats(startDate, endDate, uris, unique);
        } catch (Exception e) {
            throw new IllegalArgumentException("Неверный формат даты. Используйте 'yyyy-MM-dd HH:mm:ss'");
        }
    }
}