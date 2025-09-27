package ru.practicum.mainservice.comment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.mainservice.comment.model.Comment;
import ru.practicum.mainservice.comment.model.CommentStatus;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // Для публичного доступа - только активные комментарии определенного события
    Page<Comment> findByEventIdAndStatusInOrderByCreatedAtDesc(Long eventId, List<CommentStatus> statuses, Pageable pageable);

    // Для пользователя - все его комментарии кроме удаленных
    Page<Comment> findByUserIdAndStatusNotOrderByCreatedAtDesc(Long userId, CommentStatus status, Pageable pageable);

    // Для админа - комментарии с фильтрацией по событиям, пользователям и статусам
    @Query("SELECT c FROM Comment c WHERE " +
            "(:eventIds IS NULL OR c.event.id IN :eventIds) AND " +
            "(:userIds IS NULL OR c.user.id IN :userIds) AND " +
            "(:statuses IS NULL OR c.status IN :statuses) " +
            "ORDER BY c.createdAt DESC")
    Page<Comment> findByEventIdInAndUserIdInAndStatusInOrderByCreatedAtDesc(
            @Param("eventIds") List<Long> eventIds,
            @Param("userIds") List<Long> userIds,
            @Param("statuses") List<CommentStatus> statuses,
            Pageable pageable);

    // Проверить существование комментария у пользователя
    boolean existsByIdAndUserId(Long commentId, Long userId);
}