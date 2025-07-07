package com.iac.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.iac.service.TinyURLService;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class UTMUtils {

    @Value("${app.base-url}")
    private String baseUrl;

    @Autowired
    private TinyURLService tinyURLService;

    public String generateUTMLink(String name) {
        String encodedName = URLEncoder.encode(name, StandardCharsets.UTF_8);
        String longUrl = String.format("%s?utm_source=ca&utm_medium=email&utm_campaign=%s", 
            baseUrl, 
            encodedName
        );
        
        // Shorten the URL using TinyURL service
        System.out.println("Shortening UTM link: " + longUrl);
        String shortUrl = tinyURLService.shortenURL(longUrl);
        System.out.println("Shortened UTM link: " + shortUrl);
        
        return shortUrl;
    }
}