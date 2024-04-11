package ru.masterDetail.docsAndPositions.exceptions;

public class ValidationException extends RuntimeException {
    public ValidationException(final String message) {
            super(message);
        }
    }