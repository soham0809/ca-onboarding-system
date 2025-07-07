package com.iac.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iac.dto.JoineeDTO;
import com.iac.model.Joinee;
import com.iac.repository.JoineeRepository;
import com.iac.service.EmailService;
import com.iac.util.UTMUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(JoineeController.class)
class JoineeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JoineeRepository joineeRepository;

    @MockBean
    private EmailService emailService;

    @MockBean
    private UTMUtils utmUtils;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void submit_ValidJoinee_ReturnsSuccess() throws Exception {
        // Given
        JoineeDTO joineeDTO = new JoineeDTO();
        joineeDTO.setName("Test User");
        joineeDTO.setEmail("test@example.com");

        Joinee savedJoinee = new Joinee();
        savedJoinee.setId("test-id");
        savedJoinee.setName(joineeDTO.getName());
        savedJoinee.setEmail(joineeDTO.getEmail());
        savedJoinee.setUtmLink("https://tinyurl.com/test");
        savedJoinee.setCreatedAt(LocalDateTime.now());

        when(utmUtils.generateUTMLink(any())).thenReturn("https://tinyurl.com/test");
        when(joineeRepository.save(any())).thenReturn(savedJoinee);

        // When & Then
        mockMvc.perform(post("/api/joinee/submit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(joineeDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("test-id"))
                .andExpect(jsonPath("$.name").value(joineeDTO.getName()))
                .andExpect(jsonPath("$.email").value(joineeDTO.getEmail()));
    }

    @Test
    void submit_InvalidEmail_ReturnsBadRequest() throws Exception {
        // Given
        JoineeDTO joineeDTO = new JoineeDTO();
        joineeDTO.setName("Test User");
        joineeDTO.setEmail("invalid-email");

        // When & Then
        mockMvc.perform(post("/api/joinee/submit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(joineeDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void test_ReturnsSuccess() throws Exception {
        mockMvc.perform(get("/api/joinee/test"))
                .andExpect(status().isOk())
                .andExpect(content().string("CA Onboarding System is running!"));
    }

    @Test
    void export_ReturnsCSVFile() throws Exception {
        // Given
        Joinee joinee = new Joinee();
        joinee.setId("test-id");
        joinee.setName("Test User");
        joinee.setEmail("test@example.com");
        joinee.setUtmLink("https://tinyurl.com/test");
        joinee.setCreatedAt(LocalDateTime.now());

        when(joineeRepository.findAll()).thenReturn(Arrays.asList(joinee));

        // When & Then
        mockMvc.perform(get("/api/joinee/export"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "text/csv"))
                .andExpect(header().string("Content-Disposition", "attachment; filename=\"joinees.csv\""));
    }
}