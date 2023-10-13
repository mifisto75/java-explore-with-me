package ru.practicum.exploreWithMe.essence.category.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    long id;
    @Column(unique = true, nullable = false)
    String name;
}
