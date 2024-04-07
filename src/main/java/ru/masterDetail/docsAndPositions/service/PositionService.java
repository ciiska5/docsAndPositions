package ru.masterDetail.docsAndPositions.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.masterDetail.docsAndPositions.model.Position;
import ru.masterDetail.docsAndPositions.storage.position.PositionStorage;

/**
 * Сервис для работы с объектами Position
 */

@Service
public class PositionService {
    private final PositionStorage positionStorage;

    @Autowired
    public PositionService(PositionStorage positionStorage) {
        this.positionStorage = positionStorage;
    }

    //добавляет позицию
    public Position addPosition(Position position) {
        return positionStorage.addPosition(position);
    }

    //обновляет позицию
    public Position updatePosition(Position position) {
        return positionStorage.updatePosition(position);
    }

    //удаляет позицию
    public void deletePosition(long posNumber) {
        positionStorage.deletePosition(posNumber);
    }
}
