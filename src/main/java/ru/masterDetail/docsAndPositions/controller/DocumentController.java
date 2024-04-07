package ru.masterDetail.docsAndPositions.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.masterDetail.docsAndPositions.model.Document;
import ru.masterDetail.docsAndPositions.service.DocumentService;

/**
 * Контроллер для работы с объектами Document
 */

@RestController
@RequestMapping("/documents")
public class DocumentController {
    private final DocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    //добавляет документ
    @RequestMapping(method = RequestMethod.POST)
    public Document addDocument(@Valid @RequestBody Document document) {
        return documentService.addDocument(document);
    }

    //получает документ по номеру
    @RequestMapping(value = "/{docNumber}", method = RequestMethod.GET)
    public Document getDocumentByNum(@PathVariable Long docNumber) {
        return documentService.getDocumentByNum(docNumber);
    }

    //редактирует документ
    @RequestMapping(method = RequestMethod.PUT)
    public Document updateDocument(@Valid @RequestBody Document document) {
        return documentService.updateDocument(document);
    }

    //удаляет документ
    @RequestMapping(value = "/{docNumber}", method = RequestMethod.DELETE)
    public void deleteDocument(@PathVariable Long docNumber) {
        documentService.deleteDocument(docNumber);
    }
}
