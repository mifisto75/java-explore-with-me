package ru.practicum.exploreWithMe.essence.event.dto.request;

import lombok.Data;
import ru.practicum.exploreWithMe.essence.request.dto.ParticipationRequestDto;

import java.util.List;

@Data
public class EventRequestStatusUpdateResult {
    List<ParticipationRequestDto> confirmedRequests;
    List<ParticipationRequestDto> rejectedRequests;
}
