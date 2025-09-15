package ru.practicum.mainservice.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.Exception.validation.Marker;
import ru.practicum.mainservice.user.dto.UserDto;
import ru.practicum.mainservice.user.service.UserService;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto addUser(@Validated(Marker.OnCreate.class) @RequestBody UserDto user) {
        return userService.saveUser(user);
    }

    @GetMapping
    public List<UserDto> FindUsers(@RequestParam(required = false) List<Long> ids,
                                   @RequestParam(defaultValue = "0") Long from,
                                   @RequestParam(defaultValue = "10") Long size) {
        return userService.FindUsers(ids, from, size);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long userId) {
        userService.delete(userId);
    }
}
