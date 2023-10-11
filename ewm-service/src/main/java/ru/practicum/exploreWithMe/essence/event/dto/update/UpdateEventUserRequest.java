package ru.practicum.exploreWithMe.essence.event.dto.update;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.practicum.exploreWithMe.essence.auxiliary.enums.UserStateAction;
import ru.practicum.exploreWithMe.essence.location.LocationDto;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class UpdateEventUserRequest {
    @Size(min = 20, max = 2000)
    String annotation; // Краткое описание  annotation
    Long category; // Категория category
    @Size(min = 20, max = 7000)
    String description; //Новое описание description
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime eventDate; // eventDate
    @Valid
    LocationDto location;
    Boolean paid; // ужно ли оплачивать участие paid
    @PositiveOrZero
    Integer participantLimit; // Ограничение на количество участников. Значение 0 - означает отсутствие ограничения participantLimit
    Boolean requestModeration; // Нужна ли пре-модерация заявок на участие requestModeration

    UserStateAction stateAction; // Новое состояние события stateAction
    @Size(min = 3, max = 120)
    String title; // Заголовок title
}
