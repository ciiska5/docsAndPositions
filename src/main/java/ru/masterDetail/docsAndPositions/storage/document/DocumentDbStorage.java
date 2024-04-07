package ru.masterDetail.docsAndPositions.storage.document;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.masterDetail.docsAndPositions.model.Document;

@Component
@Slf4j
@RequiredArgsConstructor
public class DocumentDbStorage implements DocumentStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Document addDocument(Document document) {
        return new Document();
    }

    @Override
    public Document getDocumentByNum(Long docNum) {
        return new Document();
    }

    @Override
    public Document updateDocument(Document document) {
        return null;
    }

    @Override
    public void deleteDocument(Long docNum) {

    }
}
