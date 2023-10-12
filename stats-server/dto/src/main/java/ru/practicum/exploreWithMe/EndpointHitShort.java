package ru.practicum.exploreWithMe;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EndpointHitShort {
    String app;
    String uri;
    String ip;
    String timestamp;
}
