package ru.masterDetail.docsAndPositions.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Модель объекта, описывающего сообщения о попытке добавления дублирующего номера документа
 *
 * @id идентификатор лога
 * @docId номер документа, к которому относиться лог
 * @logMessage сообщение лога
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class DuplicateDocError {
    private Long id;
    @NotNull
    private Long docId;
    @NotBlank
    private String logMessage;
}
