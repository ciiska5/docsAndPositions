package ru.masterDetail.docsAndPositions.storage.position;

import ru.masterDetail.docsAndPositions.model.Position;

import java.util.List;

public interface PositionStorage {
    //добавляет позицию
    Position addPosition(Position position);

    //обновляет позицию
    Position updatePosition(Position position);

    //получение позиций по номеру докумета
    List<Position> getPositionsByDocNum(Long docNum);

    //удаляет позицию
    void deletePosition(Long posNum);
}
