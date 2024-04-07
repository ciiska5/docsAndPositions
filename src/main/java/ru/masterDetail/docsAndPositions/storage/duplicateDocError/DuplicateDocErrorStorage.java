package ru.masterDetail.docsAndPositions.storage.duplicateDocError;

import ru.masterDetail.docsAndPositions.model.DuplicateDocError;

public interface DuplicateDocErrorStorage {
    //добавляет информацию о попытке добавления дублирующего номера документа
    DuplicateDocError addDuplicateLog(DuplicateDocError duplicateDocError);
}
