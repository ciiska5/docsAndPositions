DROP TABLE IF EXISTS documents, positions, duplicate_document_error;

--таблица с документами
CREATE TABLE IF NOT EXISTS documents
(
    doc_number          INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    creation_date       TIMESTAMP  WITHOUT TIME ZONE NOT NULL,
    doc_sum             FLOAT,
    note                VARCHAR
);

--таблица с позициями
CREATE TABLE IF NOT EXISTS positions
(
    pos_number          INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    doc_id              INTEGER NOT NULL,
    pos_name            VARCHAR NOT NULL,
    pos_sum             FLOAT NOT NULL,
    CONSTRAINT fk_documents_positions
            FOREIGN KEY (doc_id)
            REFERENCES documents (doc_number) ON DELETE CASCADE
);

--таблица с логами ошибок о попытке добавления документа с дублирующим номером
CREATE TABLE IF NOT EXISTS duplicate_document_error
(
    log_id    INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    doc_id              INTEGER NOT NULL,
    log_message         VARCHAR NOT NULL,
    CONSTRAINT fk_documents_duplicates
                FOREIGN KEY (doc_id)
                REFERENCES documents (doc_number) ON DELETE CASCADE
);