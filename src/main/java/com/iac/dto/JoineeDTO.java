package com.iac.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class JoineeDTO {
    
    @NotBlank(message = "Name is required")
    private String name;
    
    @Email(message = "Please provide a valid email address")
    @NotBlank(message = "Email is required")
    private String email;
}