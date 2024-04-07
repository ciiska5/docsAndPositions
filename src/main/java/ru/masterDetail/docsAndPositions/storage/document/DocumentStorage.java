package ru.masterDetail.docsAndPositions.storage.document;

import ru.masterDetail.docsAndPositions.model.Document;

public interface DocumentStorage {
    //добавляет документ
    Document addDocument(Document document);

    //получает документ по номеру
    Document getDocumentByNum(Long docNum);

    //обновляет документ
    Document updateDocument(Document document);

    //удаляет документ
    void deleteDocument(Long docNum);
}
