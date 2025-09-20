package ru.practicum.mainservice.statsclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.practicum.statsclient.client.RestClientConfig;
import ru.practicum.statsclient.client.StatsClient;

@Component
@Import(RestClientConfig.class)
public class StatsClientImpl extends StatsClient {
    @Autowired
    public StatsClientImpl(RestClient restClient) {
        super(restClient);
    }
}
