package com.minh.Online.Food.Ordering.domain.ports.out;



public interface SendEmailPort {

    void send(String toEmail, String subject, String htmlBody);
}
