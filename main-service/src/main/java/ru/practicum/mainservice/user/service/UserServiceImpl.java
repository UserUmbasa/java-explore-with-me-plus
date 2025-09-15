package ru.practicum.mainservice.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mainservice.Exception.DuplicateException;
import ru.practicum.mainservice.mapper.DtoModelMapper;
import ru.practicum.mainservice.user.dto.UserDto;
import ru.practicum.mainservice.user.model.User;
import ru.practicum.mainservice.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final DtoModelMapper dtoModelMapper;

    @Override
    public UserDto saveUser(UserDto user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicateException("такой пользователь уже есть");
        }
        User result = userRepository.save(dtoModelMapper.mapToUser(user));
        return dtoModelMapper.mapToUserDto(result);
    }
}
