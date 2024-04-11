package ru.masterDetail.docsAndPositions.storage.position;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.masterDetail.docsAndPositions.exceptions.DocumentNotFoundException;
import ru.masterDetail.docsAndPositions.exceptions.PositionNotFoundException;
import ru.masterDetail.docsAndPositions.exceptions.ValidationException;
import ru.masterDetail.docsAndPositions.model.Document;
import ru.masterDetail.docsAndPositions.model.Position;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
@RequiredArgsConstructor
public class PositionDbStorage implements PositionStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Position addPosition(Position position) {
        Long posNum = position.getPosNumber();
        if (posNum != null) {
            checkAddingPositionExistenceById(posNum);
        }

        String sql = "INSERT INTO positions (doc_id, pos_name, pos_sum) " +
                "VALUES (?, ?, ?);";

        Long docId = position.getDocId();
        checkDocumentExistenceById(docId);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql, new String[]{"pos_number"});
            stmt.setLong(1, docId);
            stmt.setString(2, position.getName());
            stmt.setDouble(3, position.getPositionSum());
            return stmt;
        }, keyHolder);

        Long posNumber = (Objects.requireNonNull(keyHolder.getKey())).longValue();
        position.setPosNumber(posNumber);

        log.info("Добавлена позиция с номером = {} к документу с номером = {}", posNumber, docId);

        //обновляем сумму документа после добавления новой позиции
        updateDocumentSum(docId);

        return position;
    }

    @Override
    public List<Position> getPositionsByDocNum(Long docNum) {
        String sql = "SELECT * FROM positions AS p " +
                "WHERE p.doc_id = ?;";

        return jdbcTemplate.query(sql, this::mapRowToPosition, docNum);
    }

    @Override
    public Position updatePosition(Position position) {
        Long posId = position.getPosNumber();
        Long docId = position.getDocId();
        String posName = position.getName();
        Double posSum = position.getPositionSum();

        checkPositionExistenceById(posId);
        checkDocumentExistenceById(docId);

        String sql = "UPDATE positions SET doc_id = ?, pos_name = ?, pos_sum = ? " +
                "WHERE pos_number = ?;";

        jdbcTemplate.update(sql,
                docId,
                posName,
                posSum,
                posId
        );

        log.info("Обновлена позиция с номером = {} к документу с номером = {}", posId, docId);

        //обновляем сумму документа после обновления позиции
        updateDocumentSum(docId);

        return getPositionByNum(posId);
    }

    @Override
    public void deletePosition(Long posNum) {
        String sql = "DELETE FROM positions " +
                "WHERE pos_number = ?;";

        Position foundPosition = checkPositionExistenceById(posNum);
        Long docId = foundPosition.getDocId();

        jdbcTemplate.update(sql, posNum);

        //обновляем сумму документа после обновления позиции
        updateDocumentSum(docId);

        log.info("Позиция с номером = {} успешно удалена.", posNum);
    }

    //имплементация маппера для Position
    private Position mapRowToPosition(ResultSet resultSet, int rowNum) throws SQLException {
        Position position = new Position();

        position.setPosNumber(resultSet.getLong("pos_number"));
        position.setDocId(resultSet.getLong("doc_id"));
        position.setName(resultSet.getString("pos_name"));
        position.setPositionSum(resultSet.getDouble("pos_sum"));

        return position;
    }

    //имплементация маппера для Document (для проверки его существования в базе)
    private Document mapRowToDocument(ResultSet resultSet, int rowNum) throws SQLException {
        Document document = new Document();

        document.setDocNumber(resultSet.getLong("doc_number"));

        return document;
    }

    //проверка существования позиции при добавлении
    private void checkAddingPositionExistenceById(Long posId) {
        String sql = "SELECT * FROM positions WHERE pos_number = ?;";

        List<Position> positionList = jdbcTemplate.query(sql, this::mapRowToPosition, posId);

        if (positionList.size() > 0) {
            log.error("Позиция с номером = {} уже существует", posId);
            throw new ValidationException(
                    "Позиция с номером = " + posId + " уже существует"
            );
        } else {
            log.info("Проверяем наличие документа для данной позиции...");
        }
    }

    //проверка существования обновляемой позиции
    private Position checkPositionExistenceById(Long posId) {
        String sql = "SELECT * FROM positions WHERE pos_number = ?;";

        List<Position> positionList = jdbcTemplate.query(sql, this::mapRowToPosition, posId);

        if (positionList.size() > 0) {
            log.info("Позиция с номером = {} существует. Проводим действие...", posId);
            return positionList.get(0);
        } else {
            log.error("Позиции с номером = {} не существует. Действие невозможно", posId);
            throw new PositionNotFoundException(
                    "Позиции с номером = " + posId + " не существует. Действие невозможно"
            );
        }
    }

    //проверка существования документа
    private void checkDocumentExistenceById(Long docId) {
        String sql = "SELECT * FROM documents WHERE doc_number = ?;";

        List<Document> documents = jdbcTemplate.query(sql, this::mapRowToDocument, docId);

        if (documents.size() > 0) {
            log.info("Документ с номером = {} существует. Добавляем к нему позицию...", docId);
        } else {
            log.error("Документа с номером = {} не существует. Невозможно добавить позицию", docId);
            throw new DocumentNotFoundException(
                    "Документа с номером = " + docId + " не существует. Невозможно добавить позицию"
            );
        }
    }

    //обновление суммы документа
    private void updateDocumentSum(Long docId) {
        String sql = "UPDATE documents SET doc_sum = ? WHERE doc_number = ?;";

        Double updatedDocSum = getDocSumByDocNum(docId);

        jdbcTemplate.update(sql, updatedDocSum, docId);
    }

    //получение суммы документа после добавления или обновления позиции
    private Double getDocSumByDocNum(Long docId) {
        String sql = "SELECT SUM(pos_sum) FROM positions " +
                "WHERE doc_id = ?;";

        return jdbcTemplate.queryForObject(sql, Double.class, docId);
    }

    //получение позиции по номеру
    private Position getPositionByNum(Long posId) {
        String sql = "SELECT * FROM positions WHERE pos_number = ?;";

        List<Position> positionList = jdbcTemplate.query(sql, this::mapRowToPosition, posId);

        if (positionList.size() > 0) {
            log.info("Позиция с номером = {} получена", posId);
            return positionList.get(0);
        } else {
            throw new ValidationException(
                    "Позиция с номером = " + posId + " не найдена"
            );
        }
    }
}
