package ru.practicum.exploreWithMe.essence.location;

import lombok.Data;

import javax.persistence.Embeddable;

@Embeddable
@Data
public class Location {
    private Float lat;
    private Float lon;
}
