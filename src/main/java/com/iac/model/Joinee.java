package com.iac.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

import java.time.LocalDateTime;

@Document(collection = "joinee")
@Data
public class Joinee {
    
    @Id
    private String id;
    
    private String name;
    private String email;
    private String utmLink;
    private LocalDateTime createdAt = LocalDateTime.now();
}