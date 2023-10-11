package ru.practicum.exploreWithMe.privateApi.service;

import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.exploreWithMe.essence.auxiliary.AllRepository;
import ru.practicum.exploreWithMe.essence.auxiliary.enums.EventRequestState;
import ru.practicum.exploreWithMe.essence.auxiliary.enums.State;
import ru.practicum.exploreWithMe.essence.auxiliary.enums.UserStateAction;
import ru.practicum.exploreWithMe.essence.event.dto.EventFullDto;
import ru.practicum.exploreWithMe.essence.event.dto.EventShortDto;
import ru.practicum.exploreWithMe.essence.event.dto.NewEventDto;
import ru.practicum.exploreWithMe.essence.event.dto.request.EventRequestStatusUpdateRequest;
import ru.practicum.exploreWithMe.essence.event.dto.request.EventRequestStatusUpdateResult;
import ru.practicum.exploreWithMe.essence.event.dto.update.UpdateEventUserRequest;
import ru.practicum.exploreWithMe.essence.event.mapper.EventMapper;
import ru.practicum.exploreWithMe.essence.event.model.Event;
import ru.practicum.exploreWithMe.essence.event.model.QEvent;
import ru.practicum.exploreWithMe.essence.exeptions.BadRequest;
import ru.practicum.exploreWithMe.essence.exeptions.Conflict;
import ru.practicum.exploreWithMe.essence.request.dto.ParticipationRequestDto;
import ru.practicum.exploreWithMe.essence.request.mapper.RequestMapper;
import ru.practicum.exploreWithMe.essence.request.model.ParticipationRequest;
import ru.practicum.exploreWithMe.essence.request.model.QParticipationRequest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrivateServiceImpl implements PrivateService {
    @PersistenceContext
    private EntityManager entityManager;
    private final AllRepository allRepository;

    @Transactional(readOnly = true)
    @Override
    public List<EventShortDto> getAllEventsOneUser(Long userId, Integer from, Integer size) {
        List<Event> eventList;
        eventList = new JPAQuery<Event>(entityManager)
                .select(QEvent.event)
                .from(QEvent.event)
                .where(QEvent.event.initiator.id.in(userId))
                .orderBy(QEvent.event.eventDate.asc())
                .offset(from)
                .limit(size)
                .fetch();
        return eventList.stream().map(EventMapper::toEventShortDto).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public EventFullDto addEvent(Long userId, NewEventDto newEventDto) {
        Event event = EventMapper.toEventFromNewEvent(newEventDto);
        if (newEventDto.getEventDate().isBefore(LocalDateTime.now())) {
            throw new BadRequest("Запрос составлен некорректно");
        }
        if (newEventDto.getPaid() == null) {
            event.setPaid(false);
        }
        if (newEventDto.getParticipantLimit() == null) {
            event.setParticipantLimit(0);
        }
        if (newEventDto.getRequestModeration() == null) {
            event.setRequestModeration(true);
        }
        event.setCategory(allRepository.getCategoryById(newEventDto.getCategory()));
        event.setInitiator(allRepository.getUserById(userId));
        return EventMapper.toEventFullDto(allRepository.eventRepository.save(event));
    }

    @Transactional(readOnly = true)
    @Override
    public EventFullDto getEventByIdOneUser(Long userId, Long eventId) {
        Event event = allRepository.getEventById(eventId);
        ownerEventVerification(event.getInitiator().getId(), userId);
        return EventMapper.toEventFullDto(event);
    }

    @Transactional
    @Override
    public EventFullDto updateEventByIdOneUser(Long userId, Long eventId, UpdateEventUserRequest update) {

        Event event = allRepository.getEventById(eventId);
        if (event.getState().equals(State.PUBLISHED)) {
            throw new Conflict("Нарушение целостности данных");
        }
        ownerEventVerification(event.getInitiator().getId(), userId);
        if (update.getEventDate() != null) {
            if (update.getEventDate().isBefore(LocalDateTime.now())) {
                throw new BadRequest("Запрос составлен некорректно");
            }
        }

        Optional.ofNullable(update.getAnnotation()).ifPresent(event::setAnnotation);
        if (update.getCategory() != null) {
            event.setCategory(allRepository.getCategoryById(update.getCategory()));
        }
        if (update.getStateAction() != null) {
            if (update.getStateAction().equals(UserStateAction.SEND_TO_REVIEW)) {
                event.setState(State.PENDING);
            } else {
                event.setState(State.CANCELED);
            }
        }
        Optional.ofNullable(update.getDescription()).ifPresent(event::setDescription);
        Optional.ofNullable(update.getEventDate()).ifPresent(event::setEventDate);
        Optional.ofNullable(update.getPaid()).ifPresent(event::setPaid);
        Optional.ofNullable(update.getParticipantLimit()).ifPresent(event::setParticipantLimit);
        Optional.ofNullable(update.getRequestModeration()).ifPresent(event::setRequestModeration);
        Optional.ofNullable(update.getTitle()).ifPresent(event::setTitle);
        return EventMapper.toEventFullDto(allRepository.eventRepository.save(event));
    }

    @Transactional(readOnly = true)
    @Override
    public List<ParticipationRequestDto> getRequestsByEventId(Long userId, Long eventId) {
        Event event = allRepository.getEventById(eventId);
        ownerEventVerification(event.getInitiator().getId(), userId);
        List<ParticipationRequest> list;
        list = new JPAQuery<ParticipationRequest>(entityManager)
                .select(QParticipationRequest.participationRequest)
                .from(QParticipationRequest.participationRequest)
                .where(QParticipationRequest.participationRequest.event.id.in(eventId))
                .orderBy(QParticipationRequest.participationRequest.created.asc())
                .fetch();
        return list
                .stream()
                .map(RequestMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public EventRequestStatusUpdateResult considerationOfApplications(
            Long userId, Long eventId, EventRequestStatusUpdateRequest request) {

        Event event = allRepository.getEventById(eventId);

        ownerEventVerification(event.getInitiator().getId(), userId);

        if (event.getParticipantLimit() == 0 || !event.getRequestModeration()) {
            throw new BadRequest("Запрос составлен некорректно");
        }
        if (event.getParticipantLimit().longValue() == event.getConfirmedRequests()) {
            throw new Conflict("Нарушение целостности данных");
        }

        List<ParticipationRequest> requests = new JPAQuery<ParticipationRequest>(entityManager)
                .select(QParticipationRequest.participationRequest)
                .from(QParticipationRequest.participationRequest)
                .where(QParticipationRequest.participationRequest.id.in(request.getRequestIds()))
                .fetch();
        long confirmed = event.getConfirmedRequests();
        List<ParticipationRequest> tr = new ArrayList<>();
        List<ParticipationRequest> fol = new ArrayList<>();

        if (request.getStatus().equals(EventRequestState.CONFIRMED)) {
            for (ParticipationRequest pR : requests) {
                if (event.getParticipantLimit() > confirmed) {
                    if (pR.getStatus().equals(State.PENDING)) {
                        tr.add(pR);
                        confirmed++;
                    } else {
                        throw new Conflict("Нарушение целостности данных");
                    }
                } else {
                    if (pR.getStatus().equals(State.PENDING)) {
                        fol.add(pR);
                    } else {
                        throw new Conflict("Нарушение целостности данных");
                    }
                }
            }
        } else if (request.getStatus().equals(EventRequestState.REJECTED)) {
            for (ParticipationRequest pR : requests) {
                fol.add(pR);
            }
        }
        allRepository.requestRepository.saveAll(tr
                .stream()
                .map(pr -> {
                    pr.setStatus(State.CONFIRMED);
                    return pr;
                })
                .collect(Collectors.toList()));
        allRepository.requestRepository.saveAll(fol
                .stream()
                .map(pr -> {
                    pr.setStatus(State.REJECTED);
                    return pr;
                })
                .collect(Collectors.toList()));
        event.setConfirmedRequests(confirmed);
        allRepository.eventRepository.save(event);


        List<ParticipationRequestDto> rejectedRequests = new JPAQuery<ParticipationRequest>(entityManager)
                .select(QParticipationRequest.participationRequest)
                .from(QParticipationRequest.participationRequest)
                .where(QParticipationRequest.participationRequest.status.eq(State.REJECTED)
                        .and(QParticipationRequest.participationRequest.event.id.in(eventId)))
                .fetch()
                .stream()
                .map(RequestMapper::toDto)
                .collect(Collectors.toList());


        List<ParticipationRequestDto> confirmedRequests = new JPAQuery<ParticipationRequest>(entityManager)
                .select(QParticipationRequest.participationRequest)
                .from(QParticipationRequest.participationRequest)
                .where(QParticipationRequest.participationRequest.status.eq(State.CONFIRMED)
                        .and(QParticipationRequest.participationRequest.event.id.in(eventId)))
                .fetch()
                .stream()
                .map(RequestMapper::toDto)
                .collect(Collectors.toList());

        allRepository.eventRepository.save(event);
        EventRequestStatusUpdateResult result = new EventRequestStatusUpdateResult();
        result.setRejectedRequests(rejectedRequests);
        result.setConfirmedRequests(confirmedRequests);
        return result;
    }

    @Transactional(readOnly = true)
    @Override
    public List<ParticipationRequestDto> getAllRequestOneUser(Long userId) {
        List<ParticipationRequestDto> request = new JPAQuery<ParticipationRequest>(entityManager)
                .select(QParticipationRequest.participationRequest)
                .from(QParticipationRequest.participationRequest)
                .where(QParticipationRequest.participationRequest.requester.id.in(userId))
                .fetch()
                .stream()
                .map(RequestMapper::toDto)
                .collect(Collectors.toList());
        return request;
    }

    @Transactional
    @Override
    public ParticipationRequestDto addRequest(Long userId, Long eventId) {
        ParticipationRequest requests = new JPAQuery<ParticipationRequest>(entityManager)
                .select(QParticipationRequest.participationRequest)
                .from(QParticipationRequest.participationRequest)
                .where(QParticipationRequest.participationRequest.requester.id.in(userId)
                        .and(QParticipationRequest.participationRequest.event.id.in(eventId)))
                .fetchFirst();
        if (requests != null) {
            throw new Conflict("Нарушение целостности данных");
        }
        Event event = allRepository.getEventById(eventId);
        if (event.getInitiator().getId() == userId) {
            throw new Conflict("Нарушение целостности данных");
        }
        if (event.getState().equals(State.PENDING) || event.getState().equals(State.CANCELED)) {
            throw new Conflict("Нарушение целостности данных");
        }
        if (event.getParticipantLimit() > 0) {
            if (event.getParticipantLimit().longValue() == event.getConfirmedRequests()) {
                throw new Conflict("Нарушение целостности данных");
            }
        }

        ParticipationRequest request = new ParticipationRequest();
        request.setEvent(allRepository.getEventById(eventId));
        request.setRequester(allRepository.getUserById(userId));
        request.setCreated(LocalDateTime.now());
        if (event.getParticipantLimit().longValue() == 0) {
            request.setStatus(State.CONFIRMED);
        } else {
            request.setStatus(State.PENDING);
        }

        return RequestMapper.toDto(allRepository.requestRepository.save(request));
    }

    @Transactional
    @Override
    public ParticipationRequestDto cancellationRequest(Long userId, Long requestId) {
        ParticipationRequest request = new JPAQuery<ParticipationRequest>(entityManager)
                .select(QParticipationRequest.participationRequest)
                .from(QParticipationRequest.participationRequest)
                .where(QParticipationRequest.participationRequest.requester.id.in(userId)
                        .and(QParticipationRequest.participationRequest.id.in(requestId))).fetchFirst();
        request.setStatus(State.CANCELED);
        allRepository.requestRepository.save(request);
        return RequestMapper.toDto(request);
    }

    private void ownerEventVerification(Long eId, Long uId) {
        if (eId != uId) {
            throw new BadRequest("нельзя изменить чужие ивенты");
        }
    }


}
