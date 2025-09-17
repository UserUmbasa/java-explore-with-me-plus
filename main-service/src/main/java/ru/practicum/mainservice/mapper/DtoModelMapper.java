package ru.practicum.mainservice.mapper;

import org.mapstruct.Mapper;
import ru.practicum.mainservice.category.dto.CategoryDto;
import ru.practicum.mainservice.category.model.Category;
import ru.practicum.mainservice.events.dto.NewEventDto;
import ru.practicum.mainservice.events.model.Event;
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

    public abstract Event mapToEvent(NewEventDto event);

    public abstract NewEventDto mapToNewEventDto(Event event);
}
