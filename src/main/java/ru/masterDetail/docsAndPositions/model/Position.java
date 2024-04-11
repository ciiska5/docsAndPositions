package ru.masterDetail.docsAndPositions.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
    private Long posNumber;
    @NotNull
    private Long docId;
    @NotBlank
    private String name;
    @NotNull
    private Double positionSum;
}
