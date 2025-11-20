package com.minh.Online.Food.Ordering.adapters.infrastructure;

import com.minh.Online.Food.Ordering.application.service.EmailService;
import com.minh.Online.Food.Ordering.domain.ports.out.SendEmailPort;
import org.springframework.stereotype.Component;

@Component
public class SendEmailAdapter implements SendEmailPort {

    private final EmailService emailService;

    public SendEmailAdapter(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public void send(String toEmail, String subject, String htmlBody) {
        emailService.sendHtml(toEmail, subject, htmlBody); // map tới method hiện có
    }
}

