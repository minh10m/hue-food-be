package com.minh.Online.Food.Ordering.modules.forgot_password.dto;

import lombok.Builder;

@Builder
public record MailBody(String to, String subject, String text) {
}
