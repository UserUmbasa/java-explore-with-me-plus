package ru.practicum.mainservice.category.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.mainservice.category.model.Compilations;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CompilationsRepositoryImpl implements CompilationsRepositoryCustom  {

    private final EntityManager entityManager;
    private final CompilationsRepository compilationsRepository;

    @Override
    @Transactional
    public Compilations saveCompilationWithEvents(String title, Boolean pinned, List<Long> eventIds) {

        Compilations compilation = Compilations.builder()
                .title(title)
                .pinned(pinned)
                .build();

        compilation = compilationsRepository.save(compilation);

        if (eventIds != null && !eventIds.isEmpty()) {
            addEventsToCompilation(compilation.getId(), eventIds);

            compilation = compilationsRepository.findById(compilation.getId()).orElse(compilation);
        }

        return compilation;
    }

    @Override
    @Transactional
    public Compilations updateCompilationWithEvents(Long compilationId, String title, Boolean pinned, List<Long> eventIds) {
        Compilations compilation = compilationsRepository.findById(compilationId)
                .orElseThrow(() -> new EntityNotFoundException("Подборка не найдена."));

        if (title != null) {
            compilation.setTitle(title);
        }
        if (pinned != null) {
            compilation.setPinned(pinned);
        }

        clearCompilationEvents(compilationId);
        if (eventIds != null && !eventIds.isEmpty()) {
            addEventsToCompilation(compilationId, eventIds);
        }

        return compilationsRepository.save(compilation);
    }

    private void addEventsToCompilation(Long compilationId, List<Long> eventIds) {
        String sql = "INSERT INTO public.compilation_events (compilation_id, event_id) VALUES (?, ?)";

        for (Long eventId : eventIds) {
            entityManager.createNativeQuery(sql)
                    .setParameter(1, compilationId)
                    .setParameter(2, eventId)
                    .executeUpdate();
        }
    }

    private void clearCompilationEvents(Long compilationId) {
        String sql = "DELETE FROM public.compilation_events WHERE compilation_id = ?";
        entityManager.createNativeQuery(sql)
                .setParameter(1, compilationId)
                .executeUpdate();
    }
}
