package ru.practicum.server.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.dto.EndpointHitDTO;
import ru.practicum.server.model.Hit;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = org.mapstruct.MappingConstants.ComponentModel.SPRING)
public abstract class HitMapper {
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Mapping(target = "timestamp", expression = "java(parseTimestamp(endpointHitDTO.getTimestamp()))")
    public abstract Hit mapToHit(EndpointHitDTO endpointHitDTO);

    protected LocalDateTime parseTimestamp(String timestampString) {
        return LocalDateTime.parse(timestampString, FORMATTER);
    }
}
