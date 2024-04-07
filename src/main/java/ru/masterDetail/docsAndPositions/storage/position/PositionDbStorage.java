package ru.masterDetail.docsAndPositions.storage.position;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.masterDetail.docsAndPositions.model.Position;

@Component
@Slf4j
@RequiredArgsConstructor
public class PositionDbStorage implements PositionStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Position addPosition(Position position) {
        return null;
    }

    @Override
    public Position updatePosition(Position position) {
        return null;
    }

    @Override
    public void deletePosition(long posNum) {

    }
}
