package ru.masterDetail.docsAndPositions.exceptions;

public class DocumentNotFoundException extends RuntimeException {
    public DocumentNotFoundException(final String message) {
        super(message);
    }
}
