package ru.masterDetail.docsAndPositions.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.masterDetail.docsAndPositions.model.Position;
import ru.masterDetail.docsAndPositions.service.PositionService;

/**
 * Контроллер для работы с объектами Position
 */

@RestController
@RequestMapping("/positions")
public class PositionController {
    private final PositionService positionService;

    @Autowired
    public PositionController(PositionService positionService) {
        this.positionService = positionService;
    }

    //добавляет документ
    @RequestMapping(method = RequestMethod.POST)
    public Position addPosition(@Valid @RequestBody Position position) {
        return positionService.addPosition(position);
    }

    //редактирует документ
    @RequestMapping(method = RequestMethod.PUT)
    public Position updatePosition(@Valid @RequestBody Position position) {
        return positionService.updatePosition(position);
    }

    //удаляет документ
    @RequestMapping(value = "/{docNumber}", method = RequestMethod.DELETE)
    public void deletePosition(@PathVariable long posNumber) {
        positionService.deletePosition(posNumber);
    }
}
