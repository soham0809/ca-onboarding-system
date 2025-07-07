package com.iac.service;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TinyURLService {

    @Value("${tinyurl.api.key}")
    private String apiKey;

    private final String TINYURL_API_ENDPOINT = "https://api.tinyurl.com/create";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String shortenURL(String longUrl) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(TINYURL_API_ENDPOINT);
            
            // Set headers
            httpPost.setHeader("Authorization", "Bearer " + apiKey);
            httpPost.setHeader("Content-Type", "application/json");

            // Set request body
            String requestBody = String.format("{\"url\": \"%s\"}", longUrl);
            httpPost.setEntity(new StringEntity(requestBody, ContentType.APPLICATION_JSON));

            // Execute request
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                String responseBody = new String(response.getEntity().getContent().readAllBytes());
                JsonNode jsonNode = objectMapper.readTree(responseBody);
                
                if (response.getCode() == 200 && jsonNode.has("data") && jsonNode.get("data").has("tiny_url")) {
                    return jsonNode.get("data").get("tiny_url").asText();
                } else {
                    System.out.println("TinyURL API Error: " + responseBody);
                    return longUrl; // Fallback to original URL
                }
            }
        } catch (Exception e) {
            System.out.println("Error shortening URL: " + e.getMessage());
            e.printStackTrace();
            return longUrl; // Fallback to original URL
        }
    }
}