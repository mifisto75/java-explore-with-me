package ru.practicum.exploreWithMe.essence.exeptions.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.exploreWithMe.essence.exeptions.BadRequest;
import ru.practicum.exploreWithMe.essence.exeptions.Conflict;
import ru.practicum.exploreWithMe.essence.exeptions.NotFound;
import ru.practicum.exploreWithMe.essence.exeptions.model.ErrorResponse;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerBadRequest(final BadRequest e) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.toString() ,"BAD REQUEST", e.getMessage());
    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handlerNotFound(final NotFound e) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.toString() ,"NOT FOUND", e.getMessage());
    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handlerConflict(final Conflict e) {
        return new ErrorResponse(HttpStatus.CONFLICT.toString() ,"CONFLICT", e.getMessage());
    }
}