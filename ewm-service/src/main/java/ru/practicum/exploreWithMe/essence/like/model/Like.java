package ru.practicum.exploreWithMe.essence.like.model;

import lombok.Data;
import ru.practicum.exploreWithMe.essence.event.model.Event;
import ru.practicum.exploreWithMe.essence.user.model.User;

import javax.persistence.*;

@Entity
@Data
@Table(name = "like_list")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    Event event;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    User user;
    @Column(name = "is_positive", nullable = false)
    Boolean IsPositive;
}
