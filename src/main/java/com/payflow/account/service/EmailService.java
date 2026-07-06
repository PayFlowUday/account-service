package com.payflow.account.service;

public interface EmailService {
    void sendPasswordResetEmail(String toEmail, String resetToken);
}