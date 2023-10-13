package ru.practicum.exploreWithMe.privateApi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.exploreWithMe.essence.event.dto.EventFullDto;
import ru.practicum.exploreWithMe.essence.event.dto.EventShortDto;
import ru.practicum.exploreWithMe.essence.event.dto.NewEventDto;
import ru.practicum.exploreWithMe.essence.event.dto.request.EventRequestStatusUpdateRequest;
import ru.practicum.exploreWithMe.essence.event.dto.request.EventRequestStatusUpdateResult;
import ru.practicum.exploreWithMe.essence.event.dto.update.UpdateEventUserRequest;
import ru.practicum.exploreWithMe.essence.request.dto.ParticipationRequestDto;
import ru.practicum.exploreWithMe.privateApi.service.PrivateService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class PrivateController {
    private PrivateService privateService;

    @Autowired
    public PrivateController(PrivateService privateService) {
        this.privateService = privateService;
    }


    // Закрытый API для работы с событиями
    @GetMapping("/{userId}/events")
    public ResponseEntity<List<EventShortDto>> getAllEventsOneUser(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0", required = false) @PositiveOrZero Integer from,
            @RequestParam(defaultValue = "10", required = false) @Positive Integer size) {
        log.info("метод getAllEventsOneUser ");
        return new ResponseEntity<>(privateService.getAllEventsOneUser(userId, from, size), HttpStatus.OK);
    }

    @PostMapping("/{userId}/events")
    public ResponseEntity<EventFullDto> addEvent(@PathVariable Long userId,
                                                 @Valid @RequestBody NewEventDto newEventDto) {
        log.info("метод addEvent ");
        return new ResponseEntity<>(privateService.addEvent(userId, newEventDto), HttpStatus.CREATED);
    }

    @GetMapping("/{userId}/events/{eventId}")
    public ResponseEntity<EventFullDto> getEventByIdOneUser(@PathVariable Long userId,
                                                            @PathVariable Long eventId) {
        log.info("метод getEventByIdOneUser ");
        return new ResponseEntity<>(privateService.getEventByIdOneUser(userId, eventId), HttpStatus.OK);
    }

    @PatchMapping("/{userId}/events/{eventId}")
    public ResponseEntity<EventFullDto> updateEventByIdOneUser(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @Valid @RequestBody UpdateEventUserRequest updateEventUserRequest) {
        log.info("метод updateEventByIdOneUser ");
        return new ResponseEntity<>(privateService.updateEventByIdOneUser(userId, eventId, updateEventUserRequest), HttpStatus.OK);
    }

    @GetMapping("/{userId}/events/{eventId}/requests")
    public ResponseEntity<List<ParticipationRequestDto>> getRequestsByEventId(@PathVariable Long userId,
                                                                              @PathVariable Long eventId) {
        log.info("метод getRequestsByEventId ");
        return new ResponseEntity<>(privateService.getRequestsByEventId(userId, eventId), HttpStatus.OK);

    }

    @PatchMapping("/{userId}/events/{eventId}/requests")
    public ResponseEntity<EventRequestStatusUpdateResult> considerationOfApplications(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @RequestBody EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        log.info("метод considerationOfApplications ");
        return new ResponseEntity<>(privateService.considerationOfApplications(
                userId, eventId, eventRequestStatusUpdateRequest), HttpStatus.OK);
    }

    // Закрытый API для работы с запросами текущего пользователя на участие в событиях
    @GetMapping("/{userId}/requests")
    public ResponseEntity<List<ParticipationRequestDto>> getAllRequestOneUser(@PathVariable Long userId) {
        log.info("метод getAllRequestOneUser ");
        return new ResponseEntity<>(privateService.getAllRequestOneUser(userId), HttpStatus.OK);
    }

    @PostMapping("/{userId}/requests")
    public ResponseEntity<ParticipationRequestDto> addRequest(@PathVariable Long userId,
                                                              @RequestParam Long eventId) {
        log.info("метод addRequest ");
        return new ResponseEntity<>(privateService.addRequest(userId, eventId), HttpStatus.CREATED);
    }

    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    public ResponseEntity<ParticipationRequestDto> cancellationRequest(@PathVariable Long userId,
                                                                       @PathVariable Long requestId) {
        log.info("метод cancellationRequest ");
        return new ResponseEntity<>(privateService.cancellationRequest(userId, requestId), HttpStatus.OK);
    }

    //like
    @PostMapping("/{userId}/events/{eventId}/like")
    public ResponseEntity<EventShortDto> addLike(@PathVariable Long userId,
                                                 @PathVariable Long eventId) {
        log.info("метод addLike ");
        return new ResponseEntity<>(privateService.addLike(userId, eventId), HttpStatus.CREATED);
    }

    @DeleteMapping("/{userId}/events/{eventId}/like")
    public ResponseEntity<EventShortDto> deleteLike(@PathVariable Long userId,
                                                    @PathVariable Long eventId) {
        log.info("метод deleteLike ");
        privateService.deleteLike(userId, eventId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{userId}/events/{eventId}/dislike")
    public ResponseEntity<EventShortDto> addDislike(@PathVariable Long userId,
                                                    @PathVariable Long eventId) {
        log.info("метод addDislike ");

        return new ResponseEntity<>(privateService.addDislike(userId, eventId), HttpStatus.CREATED);
    }

    @DeleteMapping("/{userId}/events/{eventId}/dislike")
    public ResponseEntity<EventShortDto> deleteDislike(@PathVariable Long userId,
                                                       @PathVariable Long eventId) {
        log.info("метод deleteDislike ");
        privateService.deleteDislike(userId, eventId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
