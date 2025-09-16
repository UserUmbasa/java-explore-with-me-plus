package ru.practicum.mainservice.category.repository;

import ru.practicum.mainservice.category.model.Compilations;

import java.util.List;

public interface CompilationsRepositoryCustom {
    Compilations saveCompilationWithEvents(String title, Boolean pinned, List<Long> eventIds);
    Compilations updateCompilationWithEvents(Long compilationId, String title, Boolean pinned, List<Long> eventIds);
}
