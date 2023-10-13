package ru.practicum.exploreWithMe.essence.user.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserDto { // Пользователь
    long id;
    @NotBlank
    String name;
    @NotBlank
    @Email
    String email;
}
