package ru.practicum.exploreWithMe.essence.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.practicum.exploreWithMe.essence.auxiliary.enums.State;
import ru.practicum.exploreWithMe.essence.category.dto.CategoryDto;
import ru.practicum.exploreWithMe.essence.location.LocationDto;
import ru.practicum.exploreWithMe.essence.user.dto.UserShortDto;

import java.time.LocalDateTime;

@Data
public class EventFullDto {
    long id;
    String annotation; // Краткое описание
    CategoryDto category; // Категория
    Long confirmedRequests; // Количество одобренных заявок на участие в данном событии
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createdOn; // Дата и время создания события (в формате "yyyy-MM-dd HH:mm:ss")
    String description; // Полное описание события
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime eventDate; // Дата и время на которые намечено событие (в формате "yyyy-MM-dd HH:mm:ss")
    UserShortDto initiator; //Пользователь (краткая информация)
    LocationDto location;
    Boolean paid; // ужно ли оплачивать участие
    Integer participantLimit; // Ограничение на количество участников. Значение 0 - означает отсутствие ограничения
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime publishedOn; // Дата и время публикации события (в формате "yyyy-MM-dd HH:mm:ss")
    Boolean requestModeration; // Нужна ли пре-модерация заявок на участие
    State state; // Список состояний жизненного цикла события
    String title; // Заголовок
    Long views; // Количество просмотрев события
    Long like;
    Long dislike;
}
