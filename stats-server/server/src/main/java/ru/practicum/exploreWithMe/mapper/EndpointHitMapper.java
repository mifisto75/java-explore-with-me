package ru.practicum.exploreWithMe.mapper;

import ru.practicum.exploreWithMe.EndpointHitDto;
import ru.practicum.exploreWithMe.model.EndpointHit;
import ru.practicum.exploreWithMe.model.ViewStats;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EndpointHitMapper {
    private static final  DateTimeFormatter FORMAT =DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static EndpointHit toHit(EndpointHitDto dto){
        EndpointHit hit =new EndpointHit();
        hit.setId(dto.getId());
        hit.setApp(dto.getApp());
        hit.setUri(dto.getUri());
        hit.setIp(dto.getIp());
        hit.setTimestamp(LocalDateTime.parse(dto.getTimestamp().format(FORMAT),FORMAT));
        return hit;
    }
    public static EndpointHitDto toDto (EndpointHit hit){
        EndpointHitDto dto =new EndpointHitDto();
        dto.setId(hit.getId());
        dto.setApp(hit.getApp());
        dto.setUri(hit.getUri());
        dto.setIp(hit.getIp());
        dto.setTimestamp(LocalDateTime.parse(hit.getTimestamp().format(FORMAT),FORMAT));
        return dto;
    }
}
