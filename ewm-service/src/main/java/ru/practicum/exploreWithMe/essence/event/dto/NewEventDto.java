package ru.practicum.exploreWithMe.essence.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ru.practicum.exploreWithMe.essence.location.LocationDto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class NewEventDto {
    @Size(min = 20, max = 2000)
    @NotBlank
    String annotation; // Краткое описание

    @NotNull
    Long category; // id категории к которой относится событие
    @Size(min = 20, max = 7000)
    @NotBlank
    String description; // Полное описание события
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull
    LocalDateTime eventDate; // Дата и время на которые намечено событие (в формате "yyyy-MM-dd HH:mm:ss")
    @NotNull
    @Valid
     LocationDto location;
    Boolean paid; // нужно ли оплачивать участие
    @PositiveOrZero
    Integer participantLimit; // Ограничение на количество участников. Значение 0 - означает отсутствие ограничения
    Boolean requestModeration; // Нужна ли пре-модерация заявок на участие
    @Size(min = 3, max = 120)
    @NotBlank
    String title; // Заголовок

}
