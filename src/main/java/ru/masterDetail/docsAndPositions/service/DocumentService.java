package ru.masterDetail.docsAndPositions.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.masterDetail.docsAndPositions.model.Document;
import ru.masterDetail.docsAndPositions.storage.document.DocumentStorage;

/**
 * Сервис для работы с объектами Document
 */
@Service
public class DocumentService {
    private final DocumentStorage documentStorage;

    @Autowired
    public DocumentService(DocumentStorage documentStorage) {
        this.documentStorage = documentStorage;
    }

    //добавляет документ
    public Document addDocument(Document document) {
        return documentStorage.addDocument(document);
    }

    //получает документ по номеру
    public Document getDocumentByNum(Long docNum) {
        return documentStorage.getDocumentByNum(docNum);
    }

    //обновляет документ
    public Document updateDocument(Document document) {
        return documentStorage.updateDocument(document);
    }

    //удаляет документ
    public void deleteDocument(Long docNumber) {
        documentStorage.deleteDocument(docNumber);
    }
}
