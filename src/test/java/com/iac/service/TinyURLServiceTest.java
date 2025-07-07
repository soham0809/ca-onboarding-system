package com.iac.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TinyURLServiceTest {

    @InjectMocks
    private TinyURLService tinyURLService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(tinyURLService, "apiKey", "test-api-key");
    }

    @Test
    void shortenURL_ValidURL_ReturnsShortenedURL() {
        // Given
        String longUrl = "https://example.com/test?utm_source=test";

        // When
        String result = tinyURLService.shortenURL(longUrl);

        // Then
        // Note: This is an integration test that requires actual API key
        // In real scenario, we should mock the HTTP client
        assertNotNull(result, "Shortened URL should not be null");
        assertTrue(result.contains("tinyurl.com"), "Result should be a TinyURL");
    }

    @Test
    void shortenURL_EmptyURL_ReturnsNull() {
        // Given
        String longUrl = "";

        // When
        String result = tinyURLService.shortenURL(longUrl);

        // Then
        assertNull(result, "Should return null for empty URL");
    }

    @Test
    void shortenURL_NullURL_ReturnsNull() {
        // When
        String result = tinyURLService.shortenURL(null);

        // Then
        assertNull(result, "Should return null for null URL");
    }

    @Test
    void shortenURL_InvalidAPIKey_ReturnsOriginalURL() {
        // Given
        String longUrl = "https://example.com/test";
        ReflectionTestUtils.setField(tinyURLService, "apiKey", "invalid-key");

        // When
        String result = tinyURLService.shortenURL(longUrl);

        // Then
        assertEquals(longUrl, result, "Should return original URL when API key is invalid");
    }
}