package ru.practicum.exploreWithMe.essence.request.mapper;

import ru.practicum.exploreWithMe.essence.request.dto.ParticipationRequestDto;
import ru.practicum.exploreWithMe.essence.request.model.ParticipationRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RequestMapper {
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static ParticipationRequestDto toDto(ParticipationRequest request) {
        ParticipationRequestDto dto = new ParticipationRequestDto();
        dto.setId(request.getId());
        dto.setCreated(LocalDateTime.parse(request.getCreated().format(FORMAT), FORMAT));
        dto.setEvent(request.getEvent().getId());
        dto.setRequester(request.getRequester().getId());
        dto.setStatus(request.getStatus());
        return dto;
    }
}
