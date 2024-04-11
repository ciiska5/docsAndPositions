package ru.masterDetail.docsAndPositions.exceptions;

public class PositionNotFoundException extends RuntimeException {
    public PositionNotFoundException(final String message) {
        super(message);
    }
}