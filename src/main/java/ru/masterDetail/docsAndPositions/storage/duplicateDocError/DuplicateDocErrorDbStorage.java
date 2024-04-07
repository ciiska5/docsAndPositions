package ru.masterDetail.docsAndPositions.storage.duplicateDocError;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.masterDetail.docsAndPositions.model.DuplicateDocError;

@Component
@Slf4j
@RequiredArgsConstructor
public class DuplicateDocErrorDbStorage implements DuplicateDocErrorStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public DuplicateDocError addDuplicateLog(DuplicateDocError duplicateDocError) {
        return null;
    }
}
