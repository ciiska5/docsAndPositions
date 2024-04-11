package ru.masterDetail.docsAndPositions.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.masterDetail.docsAndPositions.exceptions.DocumentNotFoundException;
import ru.masterDetail.docsAndPositions.exceptions.PositionNotFoundException;
import ru.masterDetail.docsAndPositions.exceptions.ValidationException;
import ru.masterDetail.docsAndPositions.model.ErrorResponse;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler({DocumentNotFoundException.class, PositionNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundExceptions(final RuntimeException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler({ValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(final ValidationException e) {
        return new ErrorResponse(e.getMessage());
    }
}
