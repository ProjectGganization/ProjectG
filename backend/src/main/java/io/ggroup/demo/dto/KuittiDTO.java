package io.ggroup.demo.dto;

import java.math.BigDecimal;

public class KuittiDTO {

    private Integer orderId;
    private String tapahtuma;
    private String lipputyyppi;
    private BigDecimal yksikkohinta;
    private String qrCode;
    private BigDecimal yhteensa;

    public KuittiDTO(
            Integer orderId,
            String tapahtuma,
            String lipputyyppi,
            BigDecimal yksikkohinta,
            String qrCode,
            BigDecimal yhteensa) {

        this.orderId = orderId;
        this.tapahtuma = tapahtuma;
        this.lipputyyppi = lipputyyppi;
        this.yksikkohinta = yksikkohinta;
        this.qrCode = qrCode;
        this.yhteensa = yhteensa;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public String getTapahtuma() {
        return tapahtuma;
    }

    public String getLipputyyppi() {
        return lipputyyppi;
    }

    public BigDecimal getYksikkohinta() {
        return yksikkohinta;
    }

    public String getQrCode() {
        return qrCode;
    }

    public BigDecimal getYhteensa() {
        return yhteensa;
    }
}