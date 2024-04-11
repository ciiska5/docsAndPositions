package ru.masterDetail.docsAndPositions.storage.duplicateDocError;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.masterDetail.docsAndPositions.model.DuplicateDocError;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
@RequiredArgsConstructor
public class DuplicateDocErrorDbStorage implements DuplicateDocErrorStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public DuplicateDocError addDuplicateLog(DuplicateDocError duplicateDocError) {
        String sql = "INSERT INTO duplicate_document_error (doc_id, log_message) " +
                "VALUES (?, ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql, new String[]{"log_id"});
            stmt.setLong(1, duplicateDocError.getDocId());
            stmt.setString(2, duplicateDocError.getLogMessage());
            return stmt;
        }, keyHolder);

        Long logId = (Objects.requireNonNull(keyHolder.getKey())).longValue();
        duplicateDocError.setId(logId);

        log.info("Лог о попытке добавления документа с дублирующим номером добавлен в БД");
        return duplicateDocError;
    }

    @Override
    public List<DuplicateDocError> getLogByDocNum(Long docNumber) {
        String sql = "SELECT * FROM duplicate_document_error " +
                "WHERE doc_id = ?";

        List<DuplicateDocError> logList = jdbcTemplate.query(sql, this::mapRowToDuplicateDocError, docNumber);

        log.info("Получены логи к документу с номером = {}", docNumber);

        return logList;
    }

    //имплементация маппера для DuplicateDocError
    private DuplicateDocError mapRowToDuplicateDocError(ResultSet resultSet, int rowNum) throws SQLException {
        DuplicateDocError duplicateLog = new DuplicateDocError();

        duplicateLog.setId(resultSet.getLong("log_id"));
        duplicateLog.setLogMessage(resultSet.getString("log_message"));

        return duplicateLog;
    }
}
