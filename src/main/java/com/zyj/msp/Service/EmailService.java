package com.zyj.msp.Service;

public interface EmailService {

    void sendTextMailMessage(String to, String subject, String text);
}
