package ru.practicum.mainservice.participation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.mainservice.participation.model.ParticipationRequest;
import ru.practicum.mainservice.participation.model.RequestStatus;
import ru.practicum.mainservice.participation.model.RequestsCount;

import java.util.List;

public interface ParticipationRequestRepository extends JpaRepository<ParticipationRequest, Long> {
    boolean existsByRequesterIdAndEventId(Long userId, Long eventId);

    List<ParticipationRequest> findAllByRequesterId(Long userId);

    List<ParticipationRequest> findAllByEventId(Long eventId);

    Integer countByEventIdAndStatus(Long eventId, RequestStatus status);

    @Query("""
            SELECT COUNT(pr)
            FROM ParticipationRequest pr
            WHERE pr.event.id = :eventId AND pr.status = 'CONFIRMED'""")
    int countConfirmedRequestsForEvent(@Param("eventId") Long eventId);
}