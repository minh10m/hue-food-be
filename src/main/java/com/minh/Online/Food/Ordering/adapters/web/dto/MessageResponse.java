package com.minh.Online.Food.Ordering.adapters.web.dto;

import lombok.Data;

public class MessageResponse {
    private String message;

    public MessageResponse() {}
    public MessageResponse(String message) { this.message = message; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

}
