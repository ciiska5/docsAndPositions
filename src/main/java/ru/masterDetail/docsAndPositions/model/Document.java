package ru.masterDetail.docsAndPositions.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

/**
 * Модель объекта Document
 *
 * @docNumber номер документа (уникальный)
 * @date дата создания документа
 * @documentSum сумма всех позиций документа
 * @note примечание
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Document {
    private long docNumber;
    @NotNull
    @PastOrPresent(message = "date не может быть в будущем")
    private LocalDateTime date;
    @NotNull
    private double documentSum;
    private String note;
}
