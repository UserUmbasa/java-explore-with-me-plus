package ru.practicum.statsclient.client;

import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;
import ru.practicum.dto.EndpointHitDTO;
import ru.practicum.dto.ViewStatsDTO;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public abstract class StatsClient {
    private final RestClient restClient;

    public StatsClient(String serverUrl) {
        restClient = RestClient.builder()
                .baseUrl(serverUrl)
                .build();
    }

    public void saveHit(EndpointHitDTO endpointHitDto) {
        restClient.post()
                .uri("/hit")
                .contentType(MediaType.APPLICATION_JSON)
                .body(endpointHitDto)
                .retrieve()
                .toBodilessEntity();
    }

    public List<ViewStatsDTO> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {

        if (start == null || end == null) {
            throw new IllegalArgumentException("Время старта и завершения не могут быть пустыми.");
        }

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl("/stats")
                .queryParam("start", DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(start))
                .queryParam("end", DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(end))
                .queryParam("unique", unique);

        if (uris != null && !uris.isEmpty()) {
            uriBuilder.queryParam("uris", String.join(",", uris));
        }

        String url = uriBuilder.toUriString();

        ViewStatsDTO[] response = restClient.get()
                .uri(url)
                .retrieve()
                .body(ViewStatsDTO[].class);

        return Arrays.asList(response);
    }
}