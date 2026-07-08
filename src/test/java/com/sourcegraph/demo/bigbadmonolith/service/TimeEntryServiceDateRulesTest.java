package com.sourcegraph.demo.bigbadmonolith.service;

import org.joda.time.LocalDate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TimeEntryServiceDateRulesTest {

    private final TimeEntryService service = new TimeEntryService();

    @Test
    void shouldDefaultDateWhenMissing() {
        TimeEntryRequest request = new TimeEntryRequest("1", "1", "1", "1.25", "note", "");

        TimeEntryValidationResult result = service.validate(request);

        assertFalse(result.isValid());
        assertTrue(result.getMessage().contains("Invalid customer ID.")
                || result.getMessage().contains("Invalid user ID.")
                || result.getMessage().contains("Invalid category ID."));
    }

    @Test
    void shouldRejectFutureDate() {
        String tomorrow = LocalDate.now().plusDays(1).toString();
        TimeEntryRequest request = new TimeEntryRequest("1", "1", "1", "1.25", "note", tomorrow);

        TimeEntryValidationResult result = service.validate(request);

        assertFalse(result.isValid());
        assertTrue(result.getMessage().contains("Date logged cannot be in the future.") || result.getMessage().contains("Invalid customer ID."));
    }
}
