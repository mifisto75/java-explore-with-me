package ru.practicum.exploreWithMe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.List;
@Service
public class StatsClient extends BaseClient {

    @Autowired
    public StatsClient(@Value("${stats-service.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );

    }


    public ResponseEntity<Object> addState(EndpointHitDto endpointHitDto) {
        return post("/hit", endpointHitDto);
    }


    public ResponseEntity<List<ViewStatsDto>> getHit(String start, String end, String uris, Boolean unique) {
        return getHit("/stats" + "?start=" + start + "&end=" + end + "&uris=" + uris + "&unique=" + unique);
    }
}
