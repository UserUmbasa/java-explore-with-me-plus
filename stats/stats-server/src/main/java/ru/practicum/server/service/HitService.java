//package ru.practicum.server.service;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import ru.practicum.dto.EndpointHitDTO;
//import ru.practicum.dto.ViewStatsDTO;
//import ru.practicum.server.model.Hit;
//import ru.practicum.server.repository.HitRepository;
//
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class HitService {
//
//    private final HitRepository hitRepository;
//    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//
//    @Transactional
//    public void createHit(EndpointHitDTO endpointHitDTO) {
//        Hit hit = Hit.builder()
//                .app(endpointHitDTO.getApp())
//                .uri(endpointHitDTO.getUri())
//                .ip(endpointHitDTO.getIp())
//                .timestamp(LocalDateTime.parse(endpointHitDTO.getTimestamp(), FORMATTER))
//                .build();
//
//        hitRepository.save(hit);
//        log.info("Hit saved: {}", hit);
//    }
//
//    @Transactional(readOnly = true)
//    public List<ViewStatsDTO> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
//        validateDateRange(start, end);
//
//        if (Boolean.TRUE.equals(unique)) {
//            return hitRepository.getUniqueStats(start, end, uris);
//        } else {
//            return hitRepository.getStats(start, end, uris);
//        }
//    }
//
//    private void validateDateRange(LocalDateTime start, LocalDateTime end) {
//        if (start.isAfter(end)) {
//            throw new IllegalArgumentException("Start date cannot be after end date");
//        }
//    }
//}
//
//package ru.practicum.server.service;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import ru.practicum.dto.EndpointHitDTO;
//import ru.practicum.dto.ViewStatsDTO;
//import ru.practicum.server.model.Hit;
//import ru.practicum.server.repository.HitRepository;
//
//import java.net.URLDecoder;
//import java.nio.charset.StandardCharsets;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class HitService {
//
//    private final HitRepository hitRepository;
//    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//
//    @Transactional
//    public void createHit(EndpointHitDTO endpointHitDTO) {
//        Hit hit = Hit.builder()
//                .app(endpointHitDTO.getApp())
//                .uri(endpointHitDTO.getUri())
//                .ip(endpointHitDTO.getIp())
//                .timestamp(LocalDateTime.parse(endpointHitDTO.getTimestamp(), FORMATTER))
//                .build();
//
//        hitRepository.save(hit);
//        log.info("Hit saved: {}", hit);
//    }
//
////    @Transactional(readOnly = true)
////    public List<ViewStatsDTO> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
////        validateDateRange(start, end);
////
////        log.info("Getting stats: start={}, end={}, uris={}, unique={}", start, end, uris, unique);
////
////        List<ViewStatsDTO> result;
////        if (Boolean.TRUE.equals(unique)) {
////            result = hitRepository.getUniqueStats(start, end, uris != null ? uris : null);
////        } else {
////            result = hitRepository.getStats(start, end, uris != null ? uris : null);
////        }
////
////        log.info("Stats result: {}", result);
////        return result;
////    }
////
////    private void validateDateRange(LocalDateTime start, LocalDateTime end) {
////        if (start.isAfter(end)) {
////            throw new IllegalArgumentException("Start date cannot be after end date");
////        }
////    }
//
//    @Transactional(readOnly = true)
//    public List<ViewStatsDTO> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
//        validateDateRange(start, end);
//
//        log.info("Service processing - start: {}, end: {}, uris: {}, unique: {}", start, end, uris, unique);
//
//        List<ViewStatsDTO> result;
//        if (Boolean.TRUE.equals(unique)) {
//            result = hitRepository.getUniqueStats(start, end, uris != null ? uris : null);
//        } else {
//            result = hitRepository.getStats(start, end, uris != null ? uris : null);
//        }
//
//        log.info("Service result: {}", result);
//        return result;
//    }
//}

package ru.practicum.server.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.EndpointHitDTO;
import ru.practicum.dto.ViewStatsDTO;
import ru.practicum.server.model.Hit;
import ru.practicum.server.repository.HitRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HitService {

    private final HitRepository hitRepository;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Transactional
    public void createHit(EndpointHitDTO endpointHitDTO) {
        Hit hit = Hit.builder()
                .app(endpointHitDTO.getApp())
                .uri(endpointHitDTO.getUri())
                .ip(endpointHitDTO.getIp())
                .timestamp(LocalDateTime.parse(endpointHitDTO.getTimestamp(), FORMATTER))
                .build();

        hitRepository.save(hit);
        log.info("Hit saved: {}", hit);
    }

    @Transactional(readOnly = true)
    public List<ViewStatsDTO> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        validateDateRange(start, end); // Теперь этот метод существует

        log.info("Service processing - start: {}, end: {}, uris: {}, unique: {}", start, end, uris, unique);

        List<ViewStatsDTO> result;
        if (Boolean.TRUE.equals(unique)) {
            result = hitRepository.getUniqueStats(start, end, uris != null ? uris : null);
        } else {
            result = hitRepository.getStats(start, end, uris != null ? uris : null);
        }

        log.info("Service result: {}", result);
        return result;
    }

    // Добавьте этот метод!
    private void validateDateRange(LocalDateTime start, LocalDateTime end) {
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
    }
}