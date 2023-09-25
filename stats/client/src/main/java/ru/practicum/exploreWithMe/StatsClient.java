package ru.practicum.exploreWithMe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class StatsClient extends BaseClient {
    @Autowired
    public StatsClient(RestTemplate rest) {
        super(rest);
    }


    public ResponseEntity<Object> addState(EndpointHitDto endpointHitDto) {
        return post("/hit", endpointHitDto);
    }

    public ResponseEntity<Object> getState(int bookingId, int userId, Boolean approved) {
        Map<String, Object> parameters = Map.of(
                "approved", approved
        );
        return get("/" + bookingId + "?approved={approved}",  parameters);
    }
}
