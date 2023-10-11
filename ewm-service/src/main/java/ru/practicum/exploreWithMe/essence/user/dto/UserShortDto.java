package ru.practicum.exploreWithMe.essence.user.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserShortDto { // Пользователь (краткая информация)
    long id;
    @NotBlank
    String name;
}
