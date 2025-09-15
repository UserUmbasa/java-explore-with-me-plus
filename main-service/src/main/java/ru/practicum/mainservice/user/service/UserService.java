package ru.practicum.mainservice.user.service;

import ru.practicum.mainservice.user.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto saveUser(UserDto user);

    List<UserDto> FindUsers(List<Long> ids, Long from, Long size);

    void delete(Long userId);
}
