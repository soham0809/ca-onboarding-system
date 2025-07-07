package com.iac.model;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class JoineeTest {

    private Validator validator;
    private Joinee joinee;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        
        joinee = new Joinee();
        joinee.setId("test-id");
        joinee.setName("Test User");
        joinee.setEmail("test@example.com");
        joinee.setUtmLink("https://tinyurl.com/test");
        joinee.setCreatedAt(LocalDateTime.now());
    }

    @Test
    void validJoinee_NoViolations() {
        var violations = validator.validate(joinee);
        assertTrue(violations.isEmpty(), "Valid joinee should have no constraint violations");
    }

    @Test
    void invalidEmail_HasViolations() {
        joinee.setEmail("invalid-email");
        var violations = validator.validate(joinee);
        assertFalse(violations.isEmpty(), "Invalid email should have constraint violations");
        assertEquals(1, violations.size(), "Should have exactly one violation");
    }

    @Test
    void nullName_HasViolations() {
        joinee.setName(null);
        var violations = validator.validate(joinee);
        assertFalse(violations.isEmpty(), "Null name should have constraint violations");
    }

    @Test
    void emptyName_HasViolations() {
        joinee.setName("");
        var violations = validator.validate(joinee);
        assertFalse(violations.isEmpty(), "Empty name should have constraint violations");
    }

    @Test
    void nullEmail_HasViolations() {
        joinee.setEmail(null);
        var violations = validator.validate(joinee);
        assertFalse(violations.isEmpty(), "Null email should have constraint violations");
    }

    @Test
    void testToString_ContainsAllFields() {
        String toString = joinee.toString();
        
        assertTrue(toString.contains(joinee.getId()), "toString should contain id");
        assertTrue(toString.contains(joinee.getName()), "toString should contain name");
        assertTrue(toString.contains(joinee.getEmail()), "toString should contain email");
        assertTrue(toString.contains(joinee.getUtmLink()), "toString should contain utmLink");
    }

    @Test
    void testEqualsAndHashCode() {
        // Create two joinee objects with same id
        Joinee joinee1 = new Joinee();
        joinee1.setId("test-id");
        
        Joinee joinee2 = new Joinee();
        joinee2.setId("test-id");

        // Test equals
        assertEquals(joinee1, joinee2, "Joinee objects with same id should be equal");
        assertEquals(joinee1.hashCode(), joinee2.hashCode(), "Hash codes should be equal for equal objects");

        // Modify one object
        joinee2.setId("different-id");

        // Test inequality
        assertNotEquals(joinee1, joinee2, "Joinee objects with different ids should not be equal");
        assertNotEquals(joinee1.hashCode(), joinee2.hashCode(), "Hash codes should be different for unequal objects");
    }
}