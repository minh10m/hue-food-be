package com.minh.Online.Food.Ordering.modules.forgot_password.service;

import com.minh.Online.Food.Ordering.modules.forgot_password.dto.MailBody;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    public void sendEmail(MailBody mailBody) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailBody.to());
        message.setFrom("lem247358@gmail.com");
        message.setSubject(mailBody.subject());
        message.setText(mailBody.text());
        mailSender.send(message);

    }
}
