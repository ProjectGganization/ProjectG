package io.ggroup.demo.repository;

import io.ggroup.demo.dto.MyyntiraporttiDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MyyntiraporttiRepository {

    private final JdbcTemplate jdbcTemplate;

    public MyyntiraporttiRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<MyyntiraporttiDTO> haeMyyntiraportti() {
        return jdbcTemplate.query(
            """
            SELECT tapahtuma, alkuaika, lipputyyppi, kpl, yhteensa
            FROM Myyntiraportti
            """,
            (rs, i) -> new MyyntiraporttiDTO(
                rs.getString("tapahtuma"),
                rs.getTimestamp("alkuaika").toLocalDateTime(),
                rs.getString("lipputyyppi"),
                rs.getLong("kpl"),
                rs.getBigDecimal("yhteensa")
            )
        );
    }
}