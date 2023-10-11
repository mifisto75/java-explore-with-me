package ru.practicum.exploreWithMe.essence.exeptions.model;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class ErrorResponse {
    String status;
    String message;
    String reason;
    String timestamp;


    public ErrorResponse(String status ,String message , String reason) {
this.status = status;
this.message = message ;
this.reason =reason;
this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}