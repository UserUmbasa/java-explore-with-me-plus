package ru.practicum.mainservice.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mainservice.exception.DuplicateException;
import ru.practicum.mainservice.exception.NotFoundException;
import ru.practicum.mainservice.mapper.DtoModelMapper;
import ru.practicum.mainservice.user.dto.UserDto;
import ru.practicum.mainservice.user.model.User;
import ru.practicum.mainservice.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final DtoModelMapper dtoModelMapper;
    //private final StatsClient statsClient;

    @Override
    @Transactional
    public UserDto saveUser(UserDto user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicateException("такой пользователь уже есть");
        }
        User result = userRepository.save(dtoModelMapper.mapToUser(user));
        return dtoModelMapper.mapToUserDto(result);
    }

    @Override
    public List<UserDto> FindUsers(List<Long> ids, Long from, Long size) {
        if (ids != null) {
            List<User> result = userRepository.findAllById(ids);
            return result.stream().map(dtoModelMapper::mapToUserDto).toList();
        }
        Pageable pageable = PageRequest.of(
                from.intValue(),
                size.intValue()
        );
        List<User> result = userRepository.findAll(pageable).getContent();
        return result.stream()
                .map(dtoModelMapper::mapToUserDto)
                .toList();
    }

    @Override
    @Transactional
    public void delete(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("такого пользователя нет");
        }
        userRepository.deleteById(userId);
    }
}
