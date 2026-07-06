package com.payflow.account.service.impl;

import com.payflow.account.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${app.reset-token.expiry-minutes}")
    private int expiryMinutes;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendPasswordResetEmail(String toEmail, String resetToken) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("PayFlow - Reset Your Password");
            helper.setText(buildEmailBody(resetToken), true);

            mailSender.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email: " + e.getMessage());
        }
    }

    private String buildEmailBody(String resetToken) {
        return """
                <html>
                <body>
                    <h2>PayFlow - Password Reset</h2>
                    <p>You requested to reset your password.</p>
                    <p>Use this token to reset your password:</p>
                    <h3 style="color: #4CAF50;">%s</h3>
                    <p>This token expires in <b>%d minutes</b>.</p>
                    <p>If you did not request this, please ignore this email.</p>
                    <br/>
                    <p>PayFlow Team</p>
                </body>
                </html>
                """.formatted(resetToken, expiryMinutes);
    }
}