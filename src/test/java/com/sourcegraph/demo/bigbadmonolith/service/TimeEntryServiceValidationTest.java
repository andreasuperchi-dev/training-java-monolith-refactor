package com.sourcegraph.demo.bigbadmonolith.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TimeEntryServiceValidationTest {

    private final TimeEntryService service = new TimeEntryService();

    @Test
    void shouldFailWhenRequiredFieldsAreMissing() {
        TimeEntryRequest request = new TimeEntryRequest("", "", "", "", "", "");

        TimeEntryValidationResult result = service.validate(request);

        assertFalse(result.isValid());
        assertTrue(result.getMessage().contains("Customer is required."));
        assertTrue(result.getMessage().contains("User is required."));
        assertTrue(result.getMessage().contains("Category is required."));
        assertTrue(result.getMessage().contains("Hours is required."));
    }

    @Test
    void shouldFailWhenHoursAreNotPositive() {
        TimeEntryRequest request = new TimeEntryRequest("1", "1", "1", "0", "", "2026-07-08");

        TimeEntryValidationResult result = service.validate(request);

        assertFalse(result.isValid());
        assertTrue(result.getMessage().contains("Hours must be greater than zero."));
    }
}
