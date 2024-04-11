package ru.masterDetail.docsAndPositions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.masterDetail.docsAndPositions.model.DuplicateDocError;
import ru.masterDetail.docsAndPositions.storage.duplicateDocError.DuplicateDocErrorStorage;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

public class DuplicateDocErrorStorageTest {
    private final DuplicateDocErrorStorage mockDuplicateDocErrorStorage = Mockito.mock(DuplicateDocErrorStorage.class);

    private DuplicateDocError testDuplicateDocError;

    @BeforeEach
    void initDuplicateDocError() {
        testDuplicateDocError = new DuplicateDocError();
        testDuplicateDocError.setId(1L);
        testDuplicateDocError.setDocId(1L);
        testDuplicateDocError.setLogMessage("test log message");
    }

    @Test
    void shouldAddLogMessage() {
        Mockito
                .when(mockDuplicateDocErrorStorage.addDuplicateLog(any(DuplicateDocError.class)))
                .thenReturn(testDuplicateDocError);

        DuplicateDocError savedLog = mockDuplicateDocErrorStorage.addDuplicateLog(testDuplicateDocError);

        Assertions.assertEquals(savedLog, testDuplicateDocError);
    }

    @Test
    void shouldReturnLogMessages() {
        Mockito
                .when(mockDuplicateDocErrorStorage.getLogByDocNum(anyLong()))
                .thenReturn(List.of(testDuplicateDocError));

        List<DuplicateDocError> savedLogList = mockDuplicateDocErrorStorage.getLogByDocNum(0L);

        Assertions.assertEquals(savedLogList.get(0), testDuplicateDocError);
    }
}
