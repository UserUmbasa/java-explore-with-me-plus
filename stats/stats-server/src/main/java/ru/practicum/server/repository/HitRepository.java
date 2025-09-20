//package ru.practicum.server.repository;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import ru.practicum.dto.ViewStatsDTO;
//import ru.practicum.server.model.Hit;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//
//public interface HitRepository extends JpaRepository<Hit, Long> {
//
//    @Query(
//            """
//            SELECT new ru.practicum.dto.ViewStatsDTO(h.app, h.uri, COUNT(h.ip))
//            FROM Hit h
//            WHERE h.timestamp BETWEEN :start AND :end
//            AND (:uris IS NULL OR h.uri IN :uris)
//            GROUP BY h.app, h.uri
//            ORDER BY COUNT(h.ip) DESC
//            """
//    )
//    List<ViewStatsDTO> getStats(LocalDateTime start, LocalDateTime end, List<String> uris);
//
//    @Query(
//            """
//            SELECT new ru.practicum.dto.ViewStatsDTO(h.app, h.uri, COUNT(DISTINCT h.ip))
//            FROM Hit h
//            WHERE h.timestamp BETWEEN :start AND :end
//            AND (:uris IS NULL OR h.uri IN :uris)
//            GROUP BY h.app, h.uri
//            ORDER BY COUNT(DISTINCT h.ip) DESC
//            """
//    )
//    List<ViewStatsDTO> getUniqueStats(LocalDateTime start, LocalDateTime end, List<String> uris);
//}
package ru.practicum.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.dto.ViewStatsDTO;
import ru.practicum.server.model.Hit;

import java.time.LocalDateTime;
import java.util.List;

public interface HitRepository extends JpaRepository<Hit, Long> {

    @Query("""
        SELECT new ru.practicum.dto.ViewStatsDTO(h.app, h.uri, COUNT(h.ip))
        FROM Hit h
        WHERE h.timestamp BETWEEN :start AND :end
        AND (:uris IS NULL OR h.uri IN :uris)
        GROUP BY h.app, h.uri
        ORDER BY COUNT(h.ip) DESC
        """)
    List<ViewStatsDTO> getStats(@Param("start") LocalDateTime start,
                                @Param("end") LocalDateTime end,
                                @Param("uris") List<String> uris);

    @Query("""
        SELECT new ru.practicum.dto.ViewStatsDTO(h.app, h.uri, COUNT(DISTINCT h.ip))
        FROM Hit h
        WHERE h.timestamp BETWEEN :start AND :end
        AND (:uris IS NULL OR h.uri IN :uris)
        GROUP BY h.app, h.uri
        ORDER BY COUNT(DISTINCT h.ip) DESC
        """)
    List<ViewStatsDTO> getUniqueStats(@Param("start") LocalDateTime start,
                                      @Param("end") LocalDateTime end,
                                      @Param("uris") List<String> uris);
}