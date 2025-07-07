package com.iac.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class QRCodeServiceTest {

    private QRCodeService qrCodeService;

    @BeforeEach
    void setUp() {
        qrCodeService = new QRCodeService();
    }

    @Test
    void generateQRCodeBase64_ValidURL_ReturnsBase64String() {
        // Given
        String testUrl = "https://example.com/test";

        // When
        String result = qrCodeService.generateQRCodeBase64(testUrl);

        // Then
        assertNotNull(result, "QR code should not be null");
        assertTrue(result.length() > 0, "QR code should not be empty");
        assertTrue(isBase64(result), "Result should be valid base64");
    }

    @Test
    void generateQRCodeBase64_EmptyURL_ReturnsNull() {
        // Given
        String testUrl = "";

        // When
        String result = qrCodeService.generateQRCodeBase64(testUrl);

        // Then
        assertNull(result, "QR code should be null for empty URL");
    }

    @Test
    void generateQRCodeBase64_NullURL_ReturnsNull() {
        // When
        String result = qrCodeService.generateQRCodeBase64(null);

        // Then
        assertNull(result, "QR code should be null for null URL");
    }

    private boolean isBase64(String str) {
        try {
            java.util.Base64.getDecoder().decode(str);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}