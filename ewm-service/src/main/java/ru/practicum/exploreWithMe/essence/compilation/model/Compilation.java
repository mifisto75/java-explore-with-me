package ru.practicum.exploreWithMe.essence.compilation.model;

import lombok.Data;
import ru.practicum.exploreWithMe.essence.event.model.Event;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "compilations")
public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "compilation_id")
    long id;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "events_list",
            joinColumns = @JoinColumn(name = "compilation_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id"))
    private List<Event> events;
    @Column(name = "pinned")
    Boolean pinned;
    @Column(name = "title")
    String title;

}
