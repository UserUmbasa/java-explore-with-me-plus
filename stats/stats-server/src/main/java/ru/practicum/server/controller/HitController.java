//package ru.practicum.server.controller;
//
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.*;
//import ru.practicum.dto.EndpointHitDTO;
//import ru.practicum.dto.ViewStatsDTO;
//import ru.practicum.server.service.HitService;
//
//import java.net.URLDecoder;
//import java.net.URLEncoder;
//import java.nio.charset.StandardCharsets;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//@Slf4j
//public class HitController {
//
//    private final HitService hitService;
//
//    @PostMapping("/hit")
//    @ResponseStatus(HttpStatus.CREATED)
//    public void createHit(@Valid @RequestBody EndpointHitDTO endpointHitDTO) {
//        log.info("Received hit: {}", endpointHitDTO);
//        hitService.createHit(endpointHitDTO);
//    }
//
//    @GetMapping("/stats")
//    public List<ViewStatsDTO> getStats(
//            @RequestParam String start,
//            @RequestParam String end,
//            @RequestParam(required = false) List<String> uris,
//            @RequestParam(defaultValue = "false") Boolean unique) {
//
//        log.info("RAW PARAMS - start: '{}', end: '{}', uris: {}, unique: {}", start, end, uris, unique);
//
//        try {
//            String processedStart = processEncodedParameter(start);
//            String processedEnd = processEncodedParameter(end);
//
//            log.info("PROCESSED - start: '{}', end: '{}'", processedStart, processedEnd);
//
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//            String decodedStart = URLDecoder.decode(processedStart, StandardCharsets.UTF_8);
//            String decodedEnd = URLDecoder.decode(processedEnd, StandardCharsets.UTF_8);
//
//            log.info("DECODED - start: '{}', end: '{}'", decodedStart, decodedEnd);
//
//            LocalDateTime startDate = LocalDateTime.parse(decodedStart, formatter);
//            LocalDateTime endDate = LocalDateTime.parse(decodedEnd, formatter);
//
//            log.info("PARSED - start: {}, end: {}", startDate, endDate);
//
//            List<ViewStatsDTO> result = hitService.getStats(startDate, endDate, uris, unique);
//            log.info("FINAL RESULT: {}", result);
//
//            return result;
//        } catch (Exception e) {
//            log.error("ERROR parsing dates: start='{}', end='{}', error: {}", start, end, e.getMessage());
//            throw new IllegalArgumentException("Неверный формат даты. Используйте 'yyyy-MM-dd HH:mm:ss'");
//        }
//    }
//
//    private String processEncodedParameter(String param) {
//        log.info("Processing parameter: '{}'", param);
//
//        // Полностью перекодируем параметр
//        try {
//            // Сначала декодируем (на случай если уже частично закодировано)
//            String decoded = URLDecoder.decode(param, StandardCharsets.UTF_8);
//            // Затем кодируем правильно
//            String encoded = URLEncoder.encode(decoded, StandardCharsets.UTF_8);
//            log.info("Fully re-encoded: '{}' -> '{}'", param, encoded);
//            return encoded;
//        } catch (Exception e) {
//            log.warn("Failed to re-encode parameter '{}', using original", param);
//            return param;
//        }
//    }
//}

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

    @GetMapping("/stats")
    public List<ViewStatsDTO> getStats(
            @RequestParam String start,
            @RequestParam String end,
            @RequestParam(required = false) List<String> uris,
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

            List<ViewStatsDTO> result = hitService.getStats(startDate, endDate, uris, unique);
            log.info("FINAL RESULT: {}", result);

            return result;
        } catch (Exception e) {
            log.error("ERROR parsing dates: start='{}', end='{}', error: {}", start, end, e.getMessage());
            throw new IllegalArgumentException("Неверный формат даты. Используйте 'yyyy-MM-dd HH:mm:ss'");
        }
    }
}