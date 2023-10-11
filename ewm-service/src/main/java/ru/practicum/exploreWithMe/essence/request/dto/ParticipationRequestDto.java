package ru.practicum.exploreWithMe.essence.request.dto;

import lombok.Data;
import ru.practicum.exploreWithMe.essence.auxiliary.enums.State;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class ParticipationRequestDto {
    long id;
    @NotNull
    LocalDateTime created;
    @NotNull
    long event;
    @NotNull
    long requester;
    State status;

}
