//package ru.practicum.statsclient.client;
//
//import org.springframework.http.MediaType;
//import org.springframework.web.client.RestClient;
//import org.springframework.web.util.UriComponentsBuilder;
//import ru.practicum.dto.EndpointHitDTO;
//import ru.practicum.dto.ViewStatsDTO;
//
//import java.net.URLEncoder;
//import java.nio.charset.StandardCharsets;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.Arrays;
//import java.util.List;
//
//public class StatsClient {
//    private final RestClient restClient;
//    private final String serverUrl;
//    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//
//    public StatsClient(String serverUrl, RestClient restClient) {
//        this.restClient = restClient;
//        this.serverUrl = serverUrl;
//    }
//
//    public void saveHit(EndpointHitDTO endpointHitDto) {
//        restClient.post()
//                .uri(serverUrl + "/hit")
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(endpointHitDto)
//                .retrieve()
//                .toBodilessEntity();
//    }
//
//    public List<ViewStatsDTO> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
//        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(serverUrl + "/stats")
//                .queryParam("start", encodeDateTime(start))
//                .queryParam("end", encodeDateTime(end))
//                .queryParam("unique", unique);
//
//        if (uris != null && !uris.isEmpty()) {
//            uriBuilder.queryParam("uris", String.join(",", uris));
//        }
//
//        String url = uriBuilder.toUriString();
//
//        ViewStatsDTO[] response = restClient.get()
//                .uri(url)
//                .retrieve()
//                .body(ViewStatsDTO[].class);
//
//        return Arrays.asList(response);
//    }
//
//    private String encodeDateTime(LocalDateTime dateTime) {
//        return URLEncoder.encode(dateTime.format(FORMATTER), StandardCharsets.UTF_8);
//    }
//}

package ru.practicum.statsclient.client;

import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;
import ru.practicum.dto.EndpointHitDTO;
import ru.practicum.dto.ViewStatsDTO;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class StatsClient {
    private final RestClient restClient;
    private final String serverUrl;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public StatsClient(String serverUrl, RestClient restClient) {
        this.restClient = restClient;
        this.serverUrl = serverUrl;
    }

    public void saveHit(EndpointHitDTO endpointHitDto) {
        restClient.post()
                .uri(serverUrl + "/hit")
                .contentType(MediaType.APPLICATION_JSON)
                .body(endpointHitDto)
                .retrieve()
                .toBodilessEntity();
    }

    public List<ViewStatsDTO> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(serverUrl + "/stats")
                .queryParam("start", encodeDateTime(start))
                .queryParam("end", encodeDateTime(end))
                .queryParam("unique", unique);

        if (uris != null && !uris.isEmpty()) {
            // Правильное добавление параметра uris как массива
            for (String uri : uris) {
                uriBuilder.queryParam("uris", uri);
            }
        }

        String url = uriBuilder.toUriString();

        ViewStatsDTO[] response = restClient.get()
                .uri(url)
                .retrieve()
                .body(ViewStatsDTO[].class);

        return Arrays.asList(response);
    }

    private String encodeDateTime(LocalDateTime dateTime) {
        return URLEncoder.encode(dateTime.format(FORMATTER), StandardCharsets.UTF_8);
    }
}