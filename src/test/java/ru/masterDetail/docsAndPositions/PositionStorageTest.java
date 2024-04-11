package ru.masterDetail.docsAndPositions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.masterDetail.docsAndPositions.exceptions.DocumentNotFoundException;
import ru.masterDetail.docsAndPositions.exceptions.ValidationException;
import ru.masterDetail.docsAndPositions.model.Position;
import ru.masterDetail.docsAndPositions.storage.position.PositionStorage;

import static org.mockito.ArgumentMatchers.any;

class PositionStorageTest {
    private final PositionStorage mockPositionStorage = Mockito.mock(PositionStorage.class);
    private Position testPosition;

    @BeforeEach
    void initPosition() {
        testPosition = new Position();
        testPosition.setPosNumber(1L);
        testPosition.setDocId(1L);
        testPosition.setName("testName1");
        testPosition.setPositionSum(50.5);
    }

    @Test
    void shouldAddNewPosition() {
        Mockito
                .when(mockPositionStorage.addPosition(any(Position.class)))
                .thenReturn(testPosition);

        Position savedPosition = mockPositionStorage.addPosition(testPosition);

        Assertions.assertEquals(savedPosition, testPosition);
        Assertions.assertEquals(savedPosition.getPosNumber(), testPosition.getPosNumber());
    }

    @Test
    void shouldNotAddNewPositionWithDuplicateNumber() {
        Mockito
                .when(mockPositionStorage.addPosition(any(Position.class)))
                .thenThrow(new ValidationException("Позиция с номером 1 уже существует."));

        ValidationException error = Assertions
                .assertThrows(ValidationException.class, () -> mockPositionStorage.addPosition(testPosition));

        Assertions.assertEquals("Позиция с номером 1 уже существует.", error.getMessage());
    }

    @Test
    void shouldNotAddPositionToNotExistenceDocument() {
        Mockito
                .when(mockPositionStorage.addPosition(any(Position.class)))
                .thenThrow(new DocumentNotFoundException(
                        "Документа с номером = 1 не существует. Невозможно добавить позицию")
                );

        DocumentNotFoundException error = Assertions
                .assertThrows(DocumentNotFoundException.class, () -> mockPositionStorage.addPosition(testPosition));

        Assertions.assertEquals(
                "Документа с номером = 1 не существует. Невозможно добавить позицию", error.getMessage()
        );
    }
}
