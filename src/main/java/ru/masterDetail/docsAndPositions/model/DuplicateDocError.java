package ru.masterDetail.docsAndPositions.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Модель объекта, описывающего сообщения о попытке добавления дублирующего номера документа
 *
 * @id идентификатор лога
 * @logMessage сообщение лога
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class DuplicateDocError {
    private long id;
    @NotBlank
    private String logMessage;
}
