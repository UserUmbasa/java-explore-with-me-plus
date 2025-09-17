package ru.practicum.mainservice.events.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mainservice.events.dto.NewEventDto;
import ru.practicum.mainservice.events.repository.EventRepository;
import ru.practicum.mainservice.mapper.DtoModelMapper;
import ru.practicum.mainservice.user.dto.UserDto;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final DtoModelMapper dtoModelMapper;

    @Override
    public UserDto saveEvent(NewEventDto event) {
        return null;
    }
}
