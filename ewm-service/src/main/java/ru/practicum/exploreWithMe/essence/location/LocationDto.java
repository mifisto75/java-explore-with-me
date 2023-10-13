package ru.practicum.exploreWithMe.essence.location;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LocationDto {
    @NotNull
    private Float lat;
    @NotNull
    private Float lon;
}
