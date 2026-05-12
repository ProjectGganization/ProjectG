package io.ggroup.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MyyntiraporttiDTO {

    private String tapahtuma;
    private LocalDateTime alkuaika;
    private String lipputyyppi;
    private Long kpl;
    private BigDecimal yhteensa;

    public MyyntiraporttiDTO(
            String tapahtuma,
            LocalDateTime alkuaika,
            String lipputyyppi,
            Long kpl,
            BigDecimal yhteensa) {

        this.tapahtuma = tapahtuma;
        this.alkuaika = alkuaika;
        this.lipputyyppi = lipputyyppi;
        this.kpl = kpl;
        this.yhteensa = yhteensa;
    }

    public String getTapahtuma() { return tapahtuma; }
    public LocalDateTime getAlkuaika() { return alkuaika; }
    public String getLipputyyppi() { return lipputyyppi; }
    public Long getKpl() { return kpl; }
    public BigDecimal getYhteensa() { return yhteensa; }
}