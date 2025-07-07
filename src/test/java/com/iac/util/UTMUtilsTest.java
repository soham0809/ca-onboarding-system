package com.iac.util;

import com.iac.service.TinyURLService;
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
class UTMUtilsTest {

    @Mock
    private TinyURLService tinyURLService;

    @InjectMocks
    private UTMUtils utmUtils;

    private static final String BASE_URL = "https://example.com";
    private static final String TEST_NAME = "Test User";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(utmUtils, "baseUrl", BASE_URL);
    }

    @Test
    void generateUTMLink_ValidName_ReturnsShortened() {
        // Given
        String expectedLongUrl = BASE_URL + "?utm_source=ca&utm_medium=email&utm_campaign=Test+User";
        String expectedShortUrl = "https://tinyurl.com/test";
        when(tinyURLService.shortenURL(any())).thenReturn(expectedShortUrl);

        // When
        String result = utmUtils.generateUTMLink(TEST_NAME);

        // Then
        assertEquals(expectedShortUrl, result, "Should return shortened URL");
    }

    @Test
    void generateUTMLink_ShorteningFails_ReturnsLongUrl() {
        // Given
        String expectedLongUrl = BASE_URL + "?utm_source=ca&utm_medium=email&utm_campaign=Test+User";
        when(tinyURLService.shortenURL(any())).thenReturn(null);

        // When
        String result = utmUtils.generateUTMLink(TEST_NAME);

        // Then
        assertEquals(expectedLongUrl, result, "Should return original URL when shortening fails");
    }

    @Test
    void generateUTMLink_SpecialCharacters_EncodesCorrectly() {
        // Given
        String nameWithSpecialChars = "Test & User+";
        String expectedEncodedName = "Test+%26+User%2B";
        String expectedShortUrl = "https://tinyurl.com/test";
        when(tinyURLService.shortenURL(any())).thenReturn(expectedShortUrl);

        // When
        String result = utmUtils.generateUTMLink(nameWithSpecialChars);

        // Then
        assertEquals(expectedShortUrl, result, "Should handle special characters correctly");
    }

    @Test
    void generateUTMLink_EmptyName_ReturnsBasicUTM() {
        // Given
        String emptyName = "";
        String expectedLongUrl = BASE_URL + "?utm_source=ca&utm_medium=email&utm_campaign=";
        String expectedShortUrl = "https://tinyurl.com/test";
        when(tinyURLService.shortenURL(any())).thenReturn(expectedShortUrl);

        // When
        String result = utmUtils.generateUTMLink(emptyName);

        // Then
        assertEquals(expectedShortUrl, result, "Should handle empty name");
    }
}