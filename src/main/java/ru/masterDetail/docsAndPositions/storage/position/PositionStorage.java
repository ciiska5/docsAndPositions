package ru.masterDetail.docsAndPositions.storage.position;

import ru.masterDetail.docsAndPositions.model.Position;

public interface PositionStorage {
    //добавляет позицию
    Position addPosition(Position position);

    //обновляет позицию
    Position updatePosition(Position position);

    //удаляет позицию
    void deletePosition(long posNum);
}
