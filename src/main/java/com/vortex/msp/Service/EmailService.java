package com.vortex.msp.Service;

public interface EmailService {

    void sendTextMailMessage(String to, String subject, String text);
}
