package ru.practicum.mainservice.events.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import ru.practicum.mainservice.category.model.Category;
import ru.practicum.mainservice.category.repository.CategoryRepository;
import ru.practicum.mainservice.events.dto.*;
import ru.practicum.mainservice.events.model.Event;
import ru.practicum.mainservice.events.model.State;
import ru.practicum.mainservice.events.repository.EventRepository;
import ru.practicum.mainservice.exception.NotFoundException;
import ru.practicum.mainservice.mapper.DtoModelMapper;
import ru.practicum.mainservice.user.model.User;
import ru.practicum.mainservice.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
        Event result = dtoModelMapper.mapToEvent(event);
        result.setCategory(category);
        result.setInitiator(user);
        return dtoModelMapper.mapToEventShortDto(eventRepository.save(result));
    }

    public List<EventShortDto> findUserEvents(Long userId, Long from, Long size) {
        Pageable pageable = PageRequest.of(from.intValue(), size.intValue());
        List<Event> result = eventRepository.findAll(pageable).getContent();
        return result.stream()
                .map(dtoModelMapper::mapToEventShortDto)
                .toList();
    }

    @Override
    public EventShortDto findUserEvent(Long userId, Long eventId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("пользователя нет");
        }
        return dtoModelMapper.mapToEventShortDto(eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException("ивента нет")));
    }

    @Override
    public EventShortDto updateUserEvent(Long userId, Long eventId, UpdateEventUser event) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("пользователя нет");
        }
        Event result = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException("ивента нет"));
        if (!result.getInitiator().getId().equals(userId)) {
            throw new RuntimeException("только владелец может менять");
        }
        if (result.getState() == State.PUBLISHED) {
            throw new RuntimeException("нельзя менять опубликованные");
        }
        if (event.getEventDate() != null) {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime minAllowedDate = now.plusHours(2);
            if (event.getEventDate().isBefore(minAllowedDate)) {
                throw new RuntimeException("Дата события должна быть не ранее чем через 2 часа от текущего момента");
            }
            result.setEventDate(event.getEventDate());
        }
        Optional.ofNullable(event.getTitle()).ifPresent(result::setTitle);
        Optional.ofNullable(event.getAnnotation()).ifPresent(result::setAnnotation);
        Optional.ofNullable(event.getDescription()).ifPresent(result::setDescription);
        Optional.ofNullable(event.getPaid()).ifPresent(result::setPaid);
        Optional.ofNullable(event.getLocation()).ifPresent(loc -> {
            result.setLocationLat(loc.getLat());
            result.setLocationLon(loc.getLon());
        });
        Optional.ofNullable(event.getParticipantLimit()).ifPresent(result::setParticipantLimit);
        Optional.ofNullable(event.getRequestModeration()).ifPresent(result::setRequestModeration);
        if (event.getCategory() != null && !event.getCategory().equals(result.getCategory().getId())) {
            Category category = categoryRepository.findById(event.getCategory())
                    .orElseThrow(() -> new NotFoundException("категории нет"));
            result.setCategory(category);
        }
        if (event.getStateAction() != null) {
            switch (event.getStateAction()) {
                case SEND_TO_REVIEW -> result.setState(State.PENDING);
                case CANCEL_REVIEW  -> result.setState(State.CANCELED);
            }
        }
        Event updated = eventRepository.save(result);
        return dtoModelMapper.mapToEventShortDto(updated);
    }


    @Override
    public EventShortDto updateAdminEvent(Long eventId, UpdateEventAdminRequest event) {
        Event result = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException("ивента нет"));
        result.setState(event.getStateAction());
        return dtoModelMapper.mapToEventShortDto(result);
    }

    @Override
    public List<EventShortDto> findEventsAll(EventSearchParams params) {
        return List.of();
    }

}
