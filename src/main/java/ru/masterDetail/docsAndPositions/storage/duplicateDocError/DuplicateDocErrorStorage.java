package ru.masterDetail.docsAndPositions.storage.duplicateDocError;

import ru.masterDetail.docsAndPositions.model.DuplicateDocError;

import java.util.List;

public interface DuplicateDocErrorStorage {
    //добавляет информацию о попытке добавления дублирующего номера документа
    DuplicateDocError addDuplicateLog(DuplicateDocError duplicateDocError);

    //получает логи о попытке добавления документа с дублирующим номером
    List<DuplicateDocError> getLogByDocNum(Long docNumber);
}
