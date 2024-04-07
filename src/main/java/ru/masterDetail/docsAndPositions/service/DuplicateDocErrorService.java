package ru.masterDetail.docsAndPositions.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.masterDetail.docsAndPositions.model.DuplicateDocError;
import ru.masterDetail.docsAndPositions.storage.duplicateDocError.DuplicateDocErrorStorage;

/**
 * Сервис для работы с объектами DuplicateDocErrorService
 */
@Service
public class DuplicateDocErrorService {
    private final DuplicateDocErrorStorage duplicateDocErrorStorage;

    @Autowired
    public DuplicateDocErrorService(DuplicateDocErrorStorage duplicateDocErrorStorage) {
        this.duplicateDocErrorStorage = duplicateDocErrorStorage;
    }

    //добавляет информацию о попытке добавления дублирующего номера документа
    public DuplicateDocError addDuplicateLog(DuplicateDocError duplicateLog) {
        return duplicateDocErrorStorage.addDuplicateLog(duplicateLog);
    }
}
