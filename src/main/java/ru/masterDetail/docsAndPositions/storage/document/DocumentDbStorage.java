package ru.masterDetail.docsAndPositions.storage.document;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.masterDetail.docsAndPositions.exceptions.DocumentNotFoundException;
import ru.masterDetail.docsAndPositions.exceptions.ValidationException;
import ru.masterDetail.docsAndPositions.model.Document;
import ru.masterDetail.docsAndPositions.model.DuplicateDocError;
import ru.masterDetail.docsAndPositions.storage.duplicateDocError.DuplicateDocErrorDbStorage;
import ru.masterDetail.docsAndPositions.storage.position.PositionStorage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
@RequiredArgsConstructor
public class DocumentDbStorage implements DocumentStorage {
    private final JdbcTemplate jdbcTemplate;
    private final PositionStorage positionStorage;
    private final DuplicateDocErrorDbStorage duplicateDocErrorDbStorage;

    //добавляет документ в БД
    @Override
    public Document addDocument(Document document) {
        Long docNum = document.getDocNumber();
        if (docNum != null) {
            checkAddingDocumentExistenceById(docNum);
        }

        String sql = "INSERT INTO documents (creation_date, note) " +
                "VALUES (?, ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql, new String[]{"doc_number"});
            stmt.setTimestamp(1, Timestamp.valueOf(document.getDate()));
            stmt.setString(2, document.getNote());
            return stmt;
        }, keyHolder);

        Long docNumber = (Objects.requireNonNull(keyHolder.getKey())).longValue();
        document.setDocNumber(docNumber);

        log.info("Добавлен документ с номером = {} ", docNumber);

        return document;
    }

    //получает документ по номеру
    @Override
    public Document getDocumentByNum(Long docNum) {
        String sql = "SELECT * FROM documents AS d " +
                "WHERE d.doc_number = ?;";

        List<Document> documentList = jdbcTemplate.query(sql, this::mapRowToDocument, docNum);

        if (documentList.size() > 0) {
            log.info("Документ с номером = {} успешно получен.", docNum);
            return documentList.get(0);
        } else {
            log.error("Документ с номером = {} не найден.", docNum);
            throw new DocumentNotFoundException("Докумен с номером = " + docNum + " не найден.");
        }
    }

    //обновляет документ
    @Override
    public Document updateDocument(Document document) {
        String sql = "UPDATE documents SET creation_date = ?, note = ? " +
                "WHERE doc_number = ?;";

        Long docId = document.getDocNumber();
        getDocumentByNum(docId);

        jdbcTemplate.update(sql,
                document.getDate(),
                document.getNote(),
                docId
        );

        log.info("Документ с номером = {} успешно обновлен после получения.", docId);

        return getDocumentByNum(docId);
    }


    //удаляет документ
    @Override
    public void deleteDocument(Long docNum) {
        String sql = "DELETE FROM documents " +
                "WHERE doc_number = ?";

        //проверяем существование документа
        getDocumentByNum(docNum);
        log.info("Документ с номером = {} успешно удален после получения.", docNum);

        jdbcTemplate.update(sql, docNum);
    }

    //имплеиентация RowMapper для Document
    private Document mapRowToDocument(ResultSet resultSet, int rowNum) throws SQLException {
        Long docNum = resultSet.getLong("doc_number");

        Document document = new Document();

        document.setDocNumber(docNum);
        document.setDate(resultSet.getTimestamp("creation_date").toLocalDateTime());
        document.setDocumentSum(resultSet.getDouble("doc_sum"));
        document.setNote(resultSet.getString("note"));
        document.setPositions(positionStorage.getPositionsByDocNum(docNum));

        return document;
    }

    //проверка существования добавляемого документа
    private void checkAddingDocumentExistenceById(Long docId) {
        String sql = "SELECT * FROM documents WHERE doc_number = ?;";

        List<Document> documents = jdbcTemplate.query(sql, this::mapRowToDocument, docId);

        if (documents.size() > 0) {
            String logMessage = "Документ с номером = " + docId + " уже существует";

            DuplicateDocError duplicateDocError = new DuplicateDocError();
            duplicateDocError.setDocId(docId);
            duplicateDocError.setLogMessage(logMessage);
            duplicateDocErrorDbStorage.addDuplicateLog(duplicateDocError);

            log.error(logMessage);
            throw new ValidationException(logMessage);
        } else {
            log.info("Добавляем новый документ...");
        }
    }
}
