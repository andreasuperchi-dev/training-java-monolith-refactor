package com.sourcegraph.demo.bigbadmonolith.integration.timeentry;

import com.sourcegraph.demo.bigbadmonolith.service.TimeEntryRequest;
import com.sourcegraph.demo.bigbadmonolith.service.TimeEntryService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TimeEntryRequiredFieldsParityIT {

    @Test
    void requiredFieldsShouldReturnLegacyEquivalentValidationPrefix() {
        TimeEntryService service = new TimeEntryService();
        String message = service.log(new TimeEntryRequest("", "", "", "", "", ""));

        assertTrue(message.startsWith("Validation errors:"));
    }
}
