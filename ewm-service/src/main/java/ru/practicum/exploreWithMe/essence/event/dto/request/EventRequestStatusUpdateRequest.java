package ru.practicum.exploreWithMe.essence.event.dto.request;

import lombok.Data;
import ru.practicum.exploreWithMe.essence.auxiliary.enums.EventRequestState;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data

public class EventRequestStatusUpdateRequest {
    @NotNull
    List<Long> requestIds;
    @NotBlank
    EventRequestState status;

}
