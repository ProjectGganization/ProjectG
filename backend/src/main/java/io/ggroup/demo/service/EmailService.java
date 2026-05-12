package io.ggroup.demo.service;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;
import java.util.Map;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendHtmlEmail(String to, String subject, String html) {
        sendHtmlEmailWithInlineImages(to, subject, html, Map.of());
    }

    public void sendHtmlEmailWithInlineImages(String to, String subject, String html, Map<String, byte[]> inlineImages) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setFrom("tickets@gg.com");
            helper.setText("HTML not supported", html);

            for (Map.Entry<String, byte[]> entry : inlineImages.entrySet()) {
                helper.addInline(entry.getKey(), new ByteArrayResource(entry.getValue()), "image/png");
            }

            mailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
