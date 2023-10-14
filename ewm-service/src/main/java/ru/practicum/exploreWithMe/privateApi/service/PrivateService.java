package ru.practicum.exploreWithMe.privateApi.service;

import ru.practicum.exploreWithMe.essence.event.dto.EventFullDto;
import ru.practicum.exploreWithMe.essence.event.dto.EventShortDto;
import ru.practicum.exploreWithMe.essence.event.dto.NewEventDto;
import ru.practicum.exploreWithMe.essence.event.dto.request.EventRequestStatusUpdateRequest;
import ru.practicum.exploreWithMe.essence.event.dto.request.EventRequestStatusUpdateResult;
import ru.practicum.exploreWithMe.essence.event.dto.update.UpdateEventUserRequest;
import ru.practicum.exploreWithMe.essence.request.dto.ParticipationRequestDto;

import java.util.List;

public interface PrivateService {
    List<EventShortDto> getAllEventsOneUser(Long userId, Integer from, Integer size);

    EventFullDto addEvent(Long userId, NewEventDto newEventDto);

    EventFullDto getEventByIdOneUser(Long userId, Long eventId);

    EventFullDto updateEventByIdOneUser(Long userId, Long eventId, UpdateEventUserRequest update);

    List<ParticipationRequestDto> getRequestsByEventId(Long userId, Long eventId);

    EventRequestStatusUpdateResult considerationOfApplications(
            Long userId, Long eventId, EventRequestStatusUpdateRequest eRuR);

    List<ParticipationRequestDto> getAllRequestOneUser(Long userId);

    ParticipationRequestDto addRequest(Long userId, Long eventId);

    ParticipationRequestDto cancellationRequest(Long userId, Long requestId);

    EventShortDto addLike(Long userId, Long eventId);

    void deleteLike(Long userId, Long eventId);

    EventShortDto addDislike(Long userId, Long eventId);

    void deleteDislike(Long userId, Long eventId);
}
