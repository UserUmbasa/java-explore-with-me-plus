package ru.practicum.mainservice.comment.service;

import ru.practicum.mainservice.comment.dto.CommentCreateDto;
import ru.practicum.mainservice.comment.dto.CommentDto;
import ru.practicum.mainservice.comment.dto.CommentUpdateDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {

    // Создание комментария
    CommentDto createComment(Long userId, Long eventId, CommentCreateDto commentCreateDto);

    // Обновление комментария пользователем
    CommentDto updateComment(Long userId, Long commentId, CommentUpdateDto commentUpdateDto);

    // Удаление комментария пользователем
    void deleteCommentByUser(Long userId, Long commentId);

    // Удаление комментария администратором
    void deleteCommentByAdmin(Long commentId);

    // Получение комментария по ID
    CommentDto getComment(Long commentId);

    // Получение комментариев события
    List<CommentDto> getEventComments(Long eventId, Pageable pageable);

    // Получение комментариев пользователя
    List<CommentDto> getUserComments(Long userId, Pageable pageable);

    // Админ: получение комментариев с фильтрацией
    List<CommentDto> getCommentsAdmin(List<Long> events, List<Long> users, Pageable pageable);
}
