package com.minh.Online.Food.Ordering.application.service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    public void sendHtml(String to, String subject, String htmlContent) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(to);
            helper.setFrom("lem247358@gmail.com");
            helper.setSubject(subject);
            helper.setText(htmlContent, true); // true → HTML

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new RuntimeException("Không thể gửi email HTML", e);
        }
    }
}
