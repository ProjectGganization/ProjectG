package io.ggroup.demo.repository;

import io.ggroup.demo.dto.KuittiDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class KuittiRepository {

    private final JdbcTemplate jdbcTemplate;

    public KuittiRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<KuittiDTO> haeKuitti(Integer orderId) {
        return jdbcTemplate.query(
            """
            SELECT
                orderId,
                tapahtuma,
                lipputyyppi,
                yksikkohinta,
                qrCode,
                yhteensa
            FROM Kuitti
            WHERE orderId = ?
            """,
            (rs, i) -> new KuittiDTO(
                rs.getInt("orderId"),
                rs.getString("tapahtuma"),
                rs.getString("lipputyyppi"),
                rs.getBigDecimal("yksikkohinta"),
                rs.getString("qrCode"),
                rs.getBigDecimal("yhteensa")
            ),
            orderId
        );
    }
}