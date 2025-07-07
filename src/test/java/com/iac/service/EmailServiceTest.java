package com.iac.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private TemplateEngine templateEngine;

    @Mock
    private QRCodeService qrCodeService;

    @Mock
    private MimeMessage mimeMessage;

    @InjectMocks
    private EmailService emailService;

    private static final String TEST_EMAIL = "test@example.com";
    private static final String TEST_NAME = "Test User";
    private static final String TEST_UTM_LINK = "https://tinyurl.com/test";

    @BeforeEach
    void setUp() {
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
        when(templateEngine.process(eq("welcome-email"), any(Context.class))).thenReturn("Test Email Content");
        when(qrCodeService.generateQRCodeBase64(any())).thenReturn("test-qr-code-base64");
    }

    @Test
    void sendWelcomeEmail_ValidInputs_EmailSentSuccessfully() throws MessagingException {
        // When
        emailService.sendWelcomeEmail(TEST_EMAIL, TEST_NAME, TEST_UTM_LINK);

        // Then
        verify(mailSender, times(1)).send(any(MimeMessage.class));
        verify(templateEngine, times(1)).process(eq("welcome-email"), any(Context.class));
        verify(qrCodeService, times(1)).generateQRCodeBase64(TEST_UTM_LINK);
    }

    @Test
    void sendWelcomeEmail_QRCodeGenerationFails_EmailStillSent() throws MessagingException {
        // Given
        when(qrCodeService.generateQRCodeBase64(any())).thenReturn(null);

        // When
        emailService.sendWelcomeEmail(TEST_EMAIL, TEST_NAME, TEST_UTM_LINK);

        // Then
        verify(mailSender, times(1)).send(any(MimeMessage.class));
        verify(templateEngine, times(1)).process(eq("welcome-email"), any(Context.class));
    }

    @Test
    void sendWelcomeEmail_TemplateProcessingFails_ThrowsException() {
        // Given
        when(templateEngine.process(eq("welcome-email"), any(Context.class))).thenThrow(new RuntimeException("Template processing failed"));

        // Then
        assertThrows(RuntimeException.class, () -> {
            emailService.sendWelcomeEmail(TEST_EMAIL, TEST_NAME, TEST_UTM_LINK);
        });
    }

    @Test
    void sendWelcomeEmail_EmailSendingFails_ThrowsException() throws MessagingException {
        // Given
        doThrow(new MessagingException("Failed to send email"))
            .when(mailSender).send(any(MimeMessage.class));

        // Then
        assertThrows(MessagingException.class, () -> {
            emailService.sendWelcomeEmail(TEST_EMAIL, TEST_NAME, TEST_UTM_LINK);
        });
    }
}