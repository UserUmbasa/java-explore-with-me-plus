package ru.practicum.mainservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.mainservice.category.dto.CategoryDto;
import ru.practicum.mainservice.category.model.Category;
import ru.practicum.mainservice.events.dto.EventFullDto;
import ru.practicum.mainservice.events.dto.NewEventDto;
import ru.practicum.mainservice.events.model.Event;
import ru.practicum.mainservice.events.model.State;
import ru.practicum.mainservice.user.dto.UserDto;
import ru.practicum.mainservice.user.model.User;

/**
 * Общий для всех сущностей
 */
@Mapper(componentModel = org.mapstruct.MappingConstants.ComponentModel.SPRING)
public abstract class DtoModelMapper {
    public abstract User mapToUser(UserDto user);

    public abstract UserDto mapToUserDto(User user);

    public abstract Category mapToCategory(CategoryDto category);

    public abstract CategoryDto mapToCategoryDto(Category category);

    public Event mapToEvent(NewEventDto event) {
        Event result = new Event();
        result.setState(State.PENDING);
        result.setAnnotation(event.getAnnotation());
        result.setEventDate(event.getEventDate());
        result.setDescription(event.getDescription());
        result.setPaid(event.getPaid());
        result.setLocationLat(event.getLocation().getLat());
        result.setLocationLon(event.getLocation().getLon());
        result.setPaid(event.getPaid());
        result.setParticipantLimit(event.getParticipantLimit());
        result.setRequestModeration(event.getRequestModeration());
        result.setTitle(event.getTitle());
        return result;
    };

    public abstract EventFullDto mapToEventFullDto(Event event);
}
