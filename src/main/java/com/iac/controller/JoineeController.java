package com.iac.controller;

import com.iac.dto.JoineeDTO;
import com.iac.model.Joinee;
import com.iac.repository.JoineeRepository;
import com.iac.service.EmailService;
import com.iac.util.UTMUtils;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/joinee")
@CrossOrigin(origins = "*")
public class JoineeController {

    @Autowired
    private JoineeRepository joineeRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UTMUtils utmUtils;

    @PostMapping("/submit")
    public ResponseEntity<?> submit(@RequestBody @Valid JoineeDTO dto) {
        try {
            // Generate UTM link
            String utmLink = utmUtils.generateUTMLink(dto.getName());
            System.out.println("Generated UTM link: " + utmLink);

            // Create and save joinee
            Joinee joinee = new Joinee();
            joinee.setName(dto.getName());
            joinee.setEmail(dto.getEmail());
            joinee.setUtmLink(utmLink);
            Joinee savedJoinee = joineeRepository.save(joinee);
            System.out.println("Saved joinee with ID: " + savedJoinee.getId());

            // Send welcome email
            emailService.sendWelcomeEmail(dto.getEmail(), dto.getName(), utmLink);
            System.out.println("Welcome email sent to: " + dto.getEmail());

            return ResponseEntity.ok().body("Registration successful! Please check your email.");
            
        } catch (Exception e) {
            System.err.println("Error during registration: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                .body("An error occurred during registration: " + e.getMessage());
        }
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("CA Onboarding System is running!");
    }
}