package ru.masterDetail.docsAndPositions.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.masterDetail.docsAndPositions.model.DuplicateDocError;
import ru.masterDetail.docsAndPositions.service.DuplicateDocErrorService;

/**
 * Контроллер для работы с объектами DuplicateDocError
 */

@RestController
@RequestMapping("/duplicatelog")
public class DuplicateDocErrorController {
    private final DuplicateDocErrorService duplicateDocErrorService;

    @Autowired
    public DuplicateDocErrorController(DuplicateDocErrorService duplicateDocErrorService) {
        this.duplicateDocErrorService = duplicateDocErrorService;
    }

    //добавляет информацию о попытке добавления дублирующего номера документа
    @RequestMapping(method = RequestMethod.POST)
    public DuplicateDocError addDuplicateLog(@Valid @RequestBody DuplicateDocError duplicateLog) {
        return duplicateDocErrorService.addDuplicateLog(duplicateLog);
    }
}
