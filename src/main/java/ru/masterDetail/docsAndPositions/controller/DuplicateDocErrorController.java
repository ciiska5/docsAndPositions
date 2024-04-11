package ru.masterDetail.docsAndPositions.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.masterDetail.docsAndPositions.model.DuplicateDocError;
import ru.masterDetail.docsAndPositions.service.DuplicateDocErrorService;

import javax.validation.Valid;
import java.util.List;

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

    //получает логи о попытке добавления документа с дублирующим номером
    @RequestMapping(value = "/{docNumber}", method = RequestMethod.GET)
    public List<DuplicateDocError> addDuplicateLog(@PathVariable Long docNumber) {
        return duplicateDocErrorService.getLogByDocNum(docNumber);
    }
}
