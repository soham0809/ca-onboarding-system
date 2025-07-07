package com.iac.controller;

import com.iac.dto.JoineeDTO;
import com.iac.model.Joinee;
import com.iac.repository.JoineeRepository;
import com.iac.service.EmailService;
import com.iac.util.UTMUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/joinee")
@CrossOrigin(origins = "*")
@Tag(name = "Joinee Management", description = "APIs for managing CA joinees")
public class JoineeController {

    @Autowired
    private JoineeRepository joineeRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UTMUtils utmUtils;

    @Operation(summary = "Submit new joinee", description = "Register a new CA joinee and send welcome email")
    @ApiResponse(responseCode = "200", description = "Joinee registered successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    @PostMapping("/submit")
    public ResponseEntity<?> submit(
        @Parameter(description = "Joinee details", required = true)
        @RequestBody @Valid JoineeDTO dto) {
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

    @Operation(summary = "Test API", description = "Check if the API is running")
    @ApiResponse(responseCode = "200", description = "API is running")
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("CA Onboarding System is running!");
    }

    @Operation(summary = "Export joinees", description = "Export all joinees data to CSV format")
    @ApiResponse(responseCode = "200", description = "CSV file downloaded successfully")
    @GetMapping("/export")
    public void exportToCSV(
        @Parameter(description = "HTTP response for file download")
        HttpServletResponse response) {
        try {
            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment; filename=joinees.csv");

            // Create CSV writer
            PrintWriter writer = response.getWriter();

            // Write CSV header
            writer.println("ID,Name,Email,UTM Link,Created Date");

            // Get all joinees
            List<Joinee> joinees = joineeRepository.findAll();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            // Write joinee data
            for (Joinee joinee : joinees) {
                writer.println(String.format("%s,%s,%s,%s,%s",
                    joinee.getId(),
                    joinee.getName().replace(",", ";"), // Escape commas in name
                    joinee.getEmail(),
                    joinee.getUtmLink(),
                    joinee.getCreatedAt().format(formatter)
                ));
            }

            writer.flush();
            writer.close();
        } catch (Exception e) {
            System.out.println("Error exporting CSV: " + e.getMessage());
            e.printStackTrace();
        }
    }
}