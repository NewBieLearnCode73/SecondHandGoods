package com.dinhchieu.demo.service;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    public void sendMessage(String from ,String to, String subject, String text);
    public void sendActivationEmail(String email, String token);
}
