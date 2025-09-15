package ru.practicum.mainservice.user.service;

import ru.practicum.mainservice.user.dto.UserDto;

public interface UserService {
    UserDto saveUser(UserDto user);
}
