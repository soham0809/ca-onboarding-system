package com.iac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.util.Objects;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private QRCodeService qrCodeService;

    public void sendWelcomeEmail(String to, String name, String utmLink) throws MessagingException {
        try {
            System.out.println("Preparing welcome email for: " + name);
            System.out.println("UTM Link for QR code: " + utmLink);
            
            // Generate QR code for UTM link
            String qrCodeBase64 = qrCodeService.generateQRCodeBase64(utmLink);
            System.out.println("QR code generation result: " + (qrCodeBase64 != null ? "Success" : "Failed"));

            Context context = new Context();
            context.setVariable("name", name);
            context.setVariable("utmLink", utmLink);
            context.setVariable("qrCodeBase64", Objects.requireNonNullElse(qrCodeBase64, ""));
            System.out.println("QR code added to email context: " + (qrCodeBase64 != null && !qrCodeBase64.isEmpty()));

            String emailContent = templateEngine.process("welcome-email", context);
            System.out.println("Email template processed successfully");

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject("Welcome to Industry Academia Community!");
            helper.setText(emailContent, true);
            System.out.println("Email message prepared successfully");

            mailSender.send(message);
            System.out.println("Email sent successfully to: " + to);
            
        } catch (MessagingException e) {
            System.err.println("Failed to send welcome email: " + e.getMessage());
            throw new MessagingException("Failed to send welcome email: " + e.getMessage(), e);
        } catch (Exception e) {
            System.err.println("Unexpected error while sending email: " + e.getMessage());
            throw new MessagingException("Unexpected error while sending email", e);
        }
    }
}