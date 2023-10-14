package ru.practicum.exploreWithMe.essence.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.practicum.exploreWithMe.essence.category.dto.CategoryDto;
import ru.practicum.exploreWithMe.essence.user.dto.UserShortDto;

import java.time.LocalDateTime;

@Data
public class EventShortDto {
    String annotation; // Краткое описание
    CategoryDto category; // Категория
    Long confirmedRequests; // Количество одобренных заявок на участие в данном событии
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime eventDate; // Дата и время на которые намечено событие (в формате "yyyy-MM-dd HH:mm:ss")
    long id;
    UserShortDto initiator; //Пользователь (краткая информация)
    Boolean paid; // ужно ли оплачивать участие
    String title; // Заголовок
    Long views; // Количество просмотрев события
    Long like;
    Long dislike;
}
