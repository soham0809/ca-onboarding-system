package com.iac.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JoineeDTOTest {

    private Validator validator;
    private JoineeDTO joineeDTO;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        
        joineeDTO = new JoineeDTO();
        joineeDTO.setName("Test User");
        joineeDTO.setEmail("test@example.com");
    }

    @Test
    void validDTO_NoViolations() {
        var violations = validator.validate(joineeDTO);
        assertTrue(violations.isEmpty(), "Valid DTO should have no constraint violations");
    }

    @Test
    void invalidEmail_HasViolations() {
        joineeDTO.setEmail("invalid-email");
        var violations = validator.validate(joineeDTO);
        assertFalse(violations.isEmpty(), "Invalid email should have constraint violations");
        assertEquals(1, violations.size(), "Should have exactly one violation");
    }

    @Test
    void nullName_HasViolations() {
        joineeDTO.setName(null);
        var violations = validator.validate(joineeDTO);
        assertFalse(violations.isEmpty(), "Null name should have constraint violations");
    }

    @Test
    void emptyName_HasViolations() {
        joineeDTO.setName("");
        var violations = validator.validate(joineeDTO);
        assertFalse(violations.isEmpty(), "Empty name should have constraint violations");
    }

    @Test
    void nullEmail_HasViolations() {
        joineeDTO.setEmail(null);
        var violations = validator.validate(joineeDTO);
        assertFalse(violations.isEmpty(), "Null email should have constraint violations");
    }

    @Test
    void testToString_ContainsAllFields() {
        String toString = joineeDTO.toString();
        
        assertTrue(toString.contains(joineeDTO.getName()), "toString should contain name");
        assertTrue(toString.contains(joineeDTO.getEmail()), "toString should contain email");
    }

    @Test
    void testEqualsAndHashCode() {
        // Create two DTO objects with same values
        JoineeDTO dto1 = new JoineeDTO();
        dto1.setName("Test User");
        dto1.setEmail("test@example.com");
        
        JoineeDTO dto2 = new JoineeDTO();
        dto2.setName("Test User");
        dto2.setEmail("test@example.com");

        // Test equals
        assertEquals(dto1, dto2, "DTO objects with same values should be equal");
        assertEquals(dto1.hashCode(), dto2.hashCode(), "Hash codes should be equal for equal objects");

        // Modify one object
        dto2.setName("Different User");

        // Test inequality
        assertNotEquals(dto1, dto2, "DTO objects with different values should not be equal");
        assertNotEquals(dto1.hashCode(), dto2.hashCode(), "Hash codes should be different for unequal objects");
    }
}