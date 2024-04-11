package ru.masterDetail.docsAndPositions.model;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Модель объекта Document
 *
 * @docNumber номер документа (уникальный)
 * @date дата создания документа
 * @documentSum сумма всех позиций документа
 * @note примечание
 * @positions позиции документа
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Document {
    private Long docNumber;
    @NotNull
    @PastOrPresent(message = "Дата не может быть в будущем")
    private LocalDateTime date;
    private Double documentSum;
    private String note;
    private List<Position> positions;
}
