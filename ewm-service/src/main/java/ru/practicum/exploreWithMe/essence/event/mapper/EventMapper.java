package ru.practicum.exploreWithMe.essence.event.mapper;

import ru.practicum.exploreWithMe.essence.auxiliary.enums.State;
import ru.practicum.exploreWithMe.essence.category.mapper.CategoryMapper;
import ru.practicum.exploreWithMe.essence.event.dto.EventFullDto;
import ru.practicum.exploreWithMe.essence.event.dto.EventShortDto;
import ru.practicum.exploreWithMe.essence.event.dto.NewEventDto;
import ru.practicum.exploreWithMe.essence.event.model.Event;
import ru.practicum.exploreWithMe.essence.location.LocationMapper;
import ru.practicum.exploreWithMe.essence.user.mapper.UserMapper;

import java.time.LocalDateTime;

public class EventMapper {

    public static EventFullDto toEventFullDto(Event event) {
        EventFullDto dto = new EventFullDto();
        dto.setId(event.getId());
        dto.setAnnotation(event.getAnnotation());
        dto.setCategory(CategoryMapper.toCategoryDto(event.getCategory()));
        dto.setConfirmedRequests(event.getConfirmedRequests());
        dto.setCreatedOn(event.getCreatedOn());
        dto.setLocation(LocationMapper.toDto(event.getLocation()));
        dto.setDescription(event.getDescription());
        dto.setEventDate(event.getEventDate());
        dto.setInitiator(UserMapper.toUserShortDto(event.getInitiator()));
        dto.setPaid(event.getPaid());
        dto.setParticipantLimit(event.getParticipantLimit());
        dto.setPublishedOn(event.getPublishedOn());
        dto.setRequestModeration(event.getRequestModeration());
        dto.setState(event.getState());
        dto.setTitle(event.getTitle());
        dto.setViews(event.getViews());
        return dto;
    }

    public static EventShortDto toEventShortDto(Event event) {
        EventShortDto dto = new EventShortDto();
        dto.setId(event.getId());
        dto.setAnnotation(event.getAnnotation());
        dto.setCategory(CategoryMapper.toCategoryDto(event.getCategory()));
        dto.setConfirmedRequests(event.getConfirmedRequests());
        dto.setEventDate(event.getEventDate());
        dto.setInitiator(UserMapper.toUserShortDto(event.getInitiator()));
        dto.setPaid(event.getPaid());
        dto.setTitle(event.getTitle());
        dto.setViews(event.getViews());
        return dto;
    }

    public static Event toEventFromNewEvent(NewEventDto newEventDto) {
        Event event = new Event();
        event.setAnnotation(newEventDto.getAnnotation());
        event.setConfirmedRequests(0L);
        event.setCreatedOn(LocalDateTime.now());
        event.setDescription(newEventDto.getDescription());
        event.setLocation(LocationMapper.toLocation(newEventDto.getLocation()));
        event.setEventDate(newEventDto.getEventDate());
        event.setPaid(newEventDto.getPaid());
        event.setParticipantLimit(newEventDto.getParticipantLimit());
        event.setPublishedOn(LocalDateTime.now());
        event.setRequestModeration(newEventDto.getRequestModeration());
        event.setState(State.PENDING);
        event.setTitle(newEventDto.getTitle());
        event.setViews(0l);
        return event;
    }
}

