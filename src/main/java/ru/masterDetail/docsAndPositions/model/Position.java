package ru.masterDetail.docsAndPositions.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Модель объекта Position
 *
 * @posNumber номер позиции (уникальный)
 * @docId идентификатор документа, к которому относиться позиция
 * @name наименование позиции
 * @positionSum сумма позиции
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Position {
    private long posNumber;
    @NotNull
    private long docId;
    @NotBlank
    private String name;
    @NotNull
    private double positionSum;
}
