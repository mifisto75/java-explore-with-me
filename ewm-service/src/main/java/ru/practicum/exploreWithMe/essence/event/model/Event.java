package ru.practicum.exploreWithMe.essence.event.model;

import lombok.Data;
import ru.practicum.exploreWithMe.essence.auxiliary.enums.State;
import ru.practicum.exploreWithMe.essence.category.model.Category;
import ru.practicum.exploreWithMe.essence.location.Location;
import ru.practicum.exploreWithMe.essence.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    long id;
    @Column(name = "annotation", nullable = false)
    String annotation; // Краткое описание
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    Category category; // Категория
    @Column(name = "confirmed_requests")
    Long confirmedRequests; // Количество одобренных заявок на участие в данном событии
    @Column(name = "created_on", nullable = false)
    LocalDateTime createdOn; // Дата и время создания события (в формате "yyyy-MM-dd HH:mm:ss")
    @Column(name = "description", nullable = false)
    String description; // Полное описание события
    @Column(name = "event_date", nullable = false)
    LocalDateTime eventDate; // Дата и время на которые намечено событие (в формате "yyyy-MM-dd HH:mm:ss")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    User initiator; //Пользователь (краткая информация)
    @AttributeOverrides(value = {
            @AttributeOverride(name = "lat", column = @Column(name = "lat")),
            @AttributeOverride(name = "lon", column = @Column(name = "lon"))
    })
    Location location;
    @Column(name = "paid", nullable = false)
    Boolean paid; // ужно ли оплачивать участие
    @Column(name = "participant_limit")
    Integer participantLimit; // Ограничение на количество участников. Значение 0 - означает отсутствие ограничения
    @Column(name = "published_on")
    LocalDateTime publishedOn; // Дата и время публикации события (в формате "yyyy-MM-dd HH:mm:ss")
    @Column(name = "request_moderation")
    Boolean requestModeration; // Нужна ли пре-модерация заявок на участие
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    State state; // Список состояний жизненного цикла события
    @Column(name = "title", nullable = false)
    String title; // Заголовок
    @Column(name = "views")
    Long views; // Количество просмотрев события


}
