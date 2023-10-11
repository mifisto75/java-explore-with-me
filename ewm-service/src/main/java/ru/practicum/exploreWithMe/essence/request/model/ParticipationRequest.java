package ru.practicum.exploreWithMe.essence.request.model;

import lombok.Data;
import ru.practicum.exploreWithMe.essence.event.model.Event;
import ru.practicum.exploreWithMe.essence.auxiliary.enums.State;
import ru.practicum.exploreWithMe.essence.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "participation_requests")
public class ParticipationRequest { // Заявка на участие в событии
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participation_request_id")
    long id;
    @Column(name = "created", nullable = false)
    LocalDateTime created; // Дата и время создания заявки
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "event", nullable = false)
    Event event; // Идентификатор события
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "requester", nullable = false)
    User requester; // Идентификатор пользователя, отправившего заявку
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    State status; // Статус заявки
}
