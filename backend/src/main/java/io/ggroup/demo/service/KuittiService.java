package io.ggroup.demo.service;

import io.ggroup.demo.dto.KuittiDTO;
import io.ggroup.demo.repository.KuittiRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class KuittiService {

    private final KuittiRepository kuittiRepository;
    private final EmailService emailService;
    private final QRImageService qrImageService;
    private static final BigDecimal palvelumaksu = new BigDecimal("12.50");

    public KuittiService(KuittiRepository kuittiRepository,
                          EmailService emailService,
                          QRImageService qrImageService) {
        this.kuittiRepository = kuittiRepository;
        this.emailService = emailService;
        this.qrImageService = qrImageService;
    }

    public List<KuittiDTO> haeKuitti(Integer orderId) {
        return kuittiRepository.haeKuitti(orderId);
    }

    public void lahetaKuitti(Integer orderId, String email) {

        List<KuittiDTO> kuitti = kuittiRepository.haeKuitti(orderId);

        if (kuitti.isEmpty()) {
            throw new IllegalArgumentException("Receipt could not be found for order " + orderId);
        }

        Map<String, byte[]> inlineImages = new LinkedHashMap<>();
        for (int i = 0; i < kuitti.size(); i++) {
            String qrCode = kuitti.get(i).getQrCode();
            if (qrCode != null) {
                inlineImages.put("qr-" + i, qrImageService.toImage(qrCode, 150));
            }
        }

        String html = buildReceiptHtml(kuitti);

        emailService.sendHtmlEmailWithInlineImages(
            email,
            "Kuitti tilauksesta #" + orderId,
            html,
            inlineImages
        );
    }

    private String buildReceiptHtml(List<KuittiDTO> kuitti) {

        StringBuilder rows = new StringBuilder();

        for (int i = 0; i < kuitti.size(); i++) {
            KuittiDTO r = kuitti.get(i);
            rows.append("""
                <tr>
                    <td>%s</td>
                    <td>%s</td>
                    <td>%s €</td>
                    <td><img src="cid:qr-%d" width="120" height="120" alt="%s" /></td>
                    <td>%s</td>
                </tr>
            """.formatted(
                r.getTapahtuma(),
                r.getLipputyyppi(),
                formatMoney(r.getYksikkohinta()),
                i,
                r.getQrCode(),
                r.getQrCode()
            ));
        }

        BigDecimal total = kuitti.get(0).getYhteensa().add(palvelumaksu);

        return """
            <h2>Kiitos ostoksestasi!</h2>
            <table border="1" cellpadding="6" cellspacing="0">
                <tr>
                    <th>Tapahtuma</th>
                    <th>Lipputyyppi</th>
                    <th>Hinta</th>
                    <th>QR-koodi</th>
                    <th>Koodi</th>
                </tr>
                %s
            </table>
            <p>Palvelumaksu: %s €</p>
            <p><strong>Yhteensä: %s €</strong></p>
        """.formatted(rows, formatMoney(palvelumaksu), formatMoney(total));
    }

    private String formatMoney(BigDecimal value) {
        return String.format(Locale.forLanguageTag("fi-FI"), "%.2f", value);
    }
}
