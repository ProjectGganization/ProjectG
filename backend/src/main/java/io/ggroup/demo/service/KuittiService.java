package io.ggroup.demo.service;

import io.ggroup.demo.dto.KuittiDTO;
import io.ggroup.demo.repository.KuittiRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

@Service
public class KuittiService {

    private final KuittiRepository kuittiRepository;
    private final EmailService emailService;

    public KuittiService(KuittiRepository kuittiRepository,
                          EmailService emailService) {
        this.kuittiRepository = kuittiRepository;
        this.emailService = emailService;
    }

    public List<KuittiDTO> haeKuitti(Integer orderId) {
        return kuittiRepository.haeKuitti(orderId);
    }

    public void lahetaKuitti(Integer orderId, String email) {

        List<KuittiDTO> kuitti = kuittiRepository.haeKuitti(orderId);

        if (kuitti.isEmpty()) {
            throw new IllegalArgumentException("Receipt could not be found for order " + orderId);
        }

        String html = buildReceiptHtml(kuitti);

        emailService.sendHtmlEmail(
            email,
            "Kuitti tilauksesta #" + orderId,
            html
        );
    }

    private String buildReceiptHtml(List<KuittiDTO> kuitti) {

        StringBuilder rows = new StringBuilder();

        for (KuittiDTO r : kuitti) {
            rows.append("""
                <tr>
                    <td>%s</td>
                    <td>%s</td>
                    <td>%s €</td>
                </tr>
            """.formatted(
                r.getTapahtuma(),
                r.getLipputyyppi(),
                formatMoney(r.getYksikkohinta())
            ));
        }

        BigDecimal total = kuitti.get(0).getYhteensa();

        return """
            <h2>Kiitos ostoksestasi!</h2>
            <table border="1" cellpadding="6" cellspacing="0">
                <tr>
                    <th>Tapahtuma</th>
                    <th>Lipputyyppi</th>
                    <th>Hinta</th>
                </tr>
                %s
            </table>
            <p><strong>Yhteensä: %s €</strong></p>
        """.formatted(rows, formatMoney(total));
    }

    private String formatMoney(BigDecimal value) {
        return String.format(Locale.forLanguageTag("fi-FI"), "%.2f", value);
    }
}