package ru.practicum.mainservice.mapper;

import org.mapstruct.Mapper;
import ru.practicum.mainservice.user.dto.UserDto;
import ru.practicum.mainservice.user.model.User;

/**
 * Общий для всех сущностей
 */
@Mapper(componentModel = org.mapstruct.MappingConstants.ComponentModel.SPRING)
public abstract class DtoModelMapper {
    public abstract User mapToUser(UserDto user);

    public abstract UserDto mapToUserDto(User user);
}
