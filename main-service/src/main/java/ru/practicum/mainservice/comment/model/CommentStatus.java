package ru.practicum.mainservice.comment.model;

public enum CommentStatus {
    PUBLISHED,   // Опубликованный комментарий
    EDITED,      // Отредактированный комментарий
    DELETED      // Удаленный комментарий (soft delete)
}