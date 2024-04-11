package ru.masterDetail.docsAndPositions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.masterDetail.docsAndPositions.exceptions.DocumentNotFoundException;
import ru.masterDetail.docsAndPositions.exceptions.ValidationException;
import ru.masterDetail.docsAndPositions.model.Document;
import ru.masterDetail.docsAndPositions.model.Position;
import ru.masterDetail.docsAndPositions.storage.document.DocumentStorage;
import ru.masterDetail.docsAndPositions.storage.position.PositionStorage;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

class DocumentStorageTest {
    private final DocumentStorage mockDocumentStorage = Mockito.mock(DocumentStorage.class);
    private final PositionStorage mockPositionStorage = Mockito.mock(PositionStorage.class);
    private Document testDocument;

    @BeforeEach
    void initDocument() {
        testDocument = new Document();
        testDocument.setDocNumber(1L);
        testDocument.setDate(LocalDateTime.now());
        testDocument.setNote("testNote1");
    }

    @Test
    void shouldAddDocument() {
        Mockito
                .when(mockDocumentStorage.addDocument(any(Document.class)))
                .thenReturn(testDocument);

        Document savedDocument = mockDocumentStorage.addDocument(testDocument);

        Assertions.assertEquals(savedDocument, testDocument);
        Assertions.assertEquals(savedDocument.getDocNumber(), testDocument.getDocNumber());
    }

    @Test
    void shouldNotAddDocumentWithDuplicateNumber() {
        Mockito
                .when(mockDocumentStorage.addDocument(any(Document.class)))
                .thenThrow(new ValidationException("Документ с номером 1 уже существует."));

        ValidationException error = Assertions
                .assertThrows(ValidationException.class, () -> mockDocumentStorage.addDocument(testDocument));

        Assertions.assertEquals("Документ с номером 1 уже существует.", error.getMessage());
    }

    @Test
    void shouldReturnDocumentWithPositions() {
        Position testPosition = new Position(1L, 1L, "testPos1", 50.0);

        Mockito
                .when(mockDocumentStorage.addDocument(any(Document.class)))
                .thenReturn(testDocument);

        Mockito
                .when(mockPositionStorage.addPosition(any(Position.class)))
                .thenReturn(testPosition);

        Document savedDocument = mockDocumentStorage.addDocument(testDocument);
        Position savedPosition = mockPositionStorage.addPosition(testPosition);

        savedDocument.setDocumentSum(savedPosition.getPositionSum());
        savedDocument.setPositions(List.of(savedPosition));

        Mockito
                .when(mockDocumentStorage.getDocumentByNum(anyLong()))
                .thenReturn(savedDocument);

        Document foundDocument = mockDocumentStorage.getDocumentByNum(1L);

        Assertions.assertEquals(savedPosition.getPositionSum(), foundDocument.getDocumentSum());
        Assertions.assertEquals(savedPosition, foundDocument.getPositions().get(0));
    }

    @Test
    void shouldNotReturnNotExistenceDocument() {
        Mockito
                .when(mockDocumentStorage.getDocumentByNum(anyLong()))
                .thenThrow(new DocumentNotFoundException("Документ с номером = 3 не найден."));

        DocumentNotFoundException error = Assertions
                .assertThrows(DocumentNotFoundException.class, () -> mockDocumentStorage.getDocumentByNum(3L));

        Assertions.assertEquals("Документ с номером = 3 не найден.", error.getMessage());
    }
}
