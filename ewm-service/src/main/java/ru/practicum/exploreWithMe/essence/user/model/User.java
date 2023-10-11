package ru.practicum.exploreWithMe.essence.user.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    long id;
    @Column(length = 50, nullable = false)
    String name;
    @Column(unique = true, nullable = false)
    String email;
}
