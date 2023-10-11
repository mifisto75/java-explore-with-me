package ru.practicum.exploreWithMe.essence.event.dto.update;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.practicum.exploreWithMe.essence.auxiliary.enums.AdminStateAction;
import ru.practicum.exploreWithMe.essence.location.LocationDto;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class UpdateEventAdminRequest {
    @Size(min = 20, max = 2000)
    String annotation; // Краткое описание
    Long category; // Категория
    @Size(min = 20, max = 7000)
    String description; //Новое описание
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime eventDate; // Дата и время на которые намечено событие (в формате "yyyy-MM-dd HH:mm:ss")
    @Valid
    LocationDto location;
    Boolean paid; // ужно ли оплачивать участие
    @PositiveOrZero
    Integer participantLimit; // Ограничение на количество участников. Значение 0 - означает отсутствие ограничения
    Boolean requestModeration; // Нужна ли пре-модерация заявок на участие

    AdminStateAction stateAction; // Новое состояние события
    @Size(min = 3, max = 120)
    String title; // Заголовок

}
