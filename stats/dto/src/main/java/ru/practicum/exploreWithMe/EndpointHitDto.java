package ru.practicum.exploreWithMe;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class EndpointHitDto {
    long id;
    String app;
    String uri;
    String ip;
    LocalDateTime timestamp;
}
