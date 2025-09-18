package ru.practicum.mainservice.events.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mainservice.category.model.Category;
import ru.practicum.mainservice.category.repository.CategoryRepository;
import ru.practicum.mainservice.events.dto.EventFullDto;
import ru.practicum.mainservice.events.dto.EventShortDto;
import ru.practicum.mainservice.events.dto.NewEventDto;
import ru.practicum.mainservice.events.model.Event;
import ru.practicum.mainservice.events.repository.EventRepository;
import ru.practicum.mainservice.exception.NotFoundException;
import ru.practicum.mainservice.mapper.DtoModelMapper;
import ru.practicum.mainservice.user.model.User;
import ru.practicum.mainservice.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final DtoModelMapper dtoModelMapper;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public EventShortDto saveEvent(Long userId, NewEventDto event) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("пользователя нет"));
        Category category = categoryRepository.findById(event.getCategory()).orElseThrow(() -> new NotFoundException("категории нет"));
        Event saveEvent = dtoModelMapper.mapToEvent(event);
        saveEvent.setCategory(category);
        saveEvent.setInitiator(user);
        Event saveEvent4 = eventRepository.save(saveEvent);
        return dtoModelMapper.mapToEventShortDto(saveEvent4);
    }
}
