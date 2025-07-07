package com.iac.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class UTMUtils {

    @Value("${app.base-url}")
    private String baseUrl;

    public String generateUTMLink(String name) {
        String encodedName = URLEncoder.encode(name, StandardCharsets.UTF_8);
        return String.format("%s?utm_source=ca&utm_medium=email&utm_campaign=%s", 
            baseUrl, 
            encodedName
        );
    }
}